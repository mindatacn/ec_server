package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.global.specify.Criteria;
import com.mindata.ecserver.global.specify.Restrictions;
import com.mindata.ecserver.main.manager.EcCodeAreaManager;
import com.mindata.ecserver.main.manager.EcContactManager;
import com.mindata.ecserver.main.manager.EcVocationCodeManager;
import com.mindata.ecserver.main.manager.es.EsContactManager;
import com.mindata.ecserver.main.model.es.EsContact;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.requestbody.ContactRequestBody;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.ContactGroupVO;
import com.mindata.ecserver.main.vo.ContactVO;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/10/26.
 * 条件查询联系人数据（未推送的）
 */
@Service
public class ContactService extends BaseService {
    @Resource
    private EsContactManager esContactManager;
    @Resource
    private EcContactManager ecContactManager;
    @Resource
    private EcVocationCodeManager ecVocationCodeManager;
    @Resource
    private SearchConditionService searchConditionService;
    @Resource
    private EcCodeAreaManager ecCodeAreaManager;

    private final String COUNT = "count";
    private final long PROVINCE_MIN_COUNT = 100;
    private final long CITY_MIN_COUNT = 20;

    public EcContactEntity findById(Long id) {
        EsContact esContact = esContactManager.findById(id);

        EcContactEntity ecContactEntity = ecContactManager.findOne(id);
        if (esContact != null) {
            ecContactEntity.setMemo(esContact.getComintro());
            ecContactEntity.setJobName(esContact.getJobName());
        }

        return ecContactEntity;
    }

    @SuppressWarnings("Duplicates")
    public SimplePage<ContactVO> findByStateAndConditions(int state, ContactRequestBody
            contactRequestBody) {
        //TODO 将来新开一个接口
        searchConditionService.add(contactRequestBody);

        //公司名\详细地址\职位名称\企业简介，任何一个不为空，就走ES
        if (!StrUtil.isEmpty(contactRequestBody.getCompanyName()) || !StrUtil.isEmpty(contactRequestBody.getAddress()
        ) || !StrUtil.isEmpty(contactRequestBody.getJobName()) || !StrUtil.isEmpty(contactRequestBody.getComintro())
                || !StrUtil.isEmpty(contactRequestBody.getExtra())) {
            return esContactManager.findByRequestBody(contactRequestBody);
        }

        Criteria<EcContactEntity> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("state", state, true));
        //有手机号
        if (contactRequestBody.getHasMobile() != null && contactRequestBody.getHasMobile()) {
            criteria.add(Restrictions.ne("mobile", "", false));
        }
        //招聘信息
        if (contactRequestBody.getNeedSale() != null && contactRequestBody.getNeedSale()) {
            criteria.add(Restrictions.eq("needSale", true, false));
        }
        //来源
        if (!CollectionUtil.isEmpty(contactRequestBody.getWebsiteIds())) {
            criteria.add(Restrictions.in("websiteId", contactRequestBody.getWebsiteIds(), true));
        }
        //规模
        if (!CollectionUtil.isEmpty(contactRequestBody.getMemberSizeTags())) {
            criteria.add(Restrictions.in("memberSizeTag", contactRequestBody.getMemberSizeTags(), true));
        }
        //行业
        if (!CollectionUtil.isEmpty(contactRequestBody.getVocations())) {
            criteria.add(Restrictions.in("vocation", getVocations(contactRequestBody.getVocations()), true));
        }
        //区域
        if (!CollectionUtil.isEmpty(contactRequestBody.getProvinces())) {
            criteria.add(Restrictions.in("province", contactRequestBody.getProvinces(), true));
            List<String> cities = contactRequestBody.getCities();

            //如果勾了多个市，则用in
            if (!CollectionUtil.isEmpty(cities)) {
                cities = getCities(contactRequestBody.getProvinces(), cities);
                criteria.add(Restrictions.in("city", cities, true));
            }
        }

        int page = Constant.PAGE_NUM;
        if (contactRequestBody.getPage() != null) {
            page = contactRequestBody.getPage();
        }
        int size = Constant.PAGE_SIZE;
        if (contactRequestBody.getSize() != null) {
            size = contactRequestBody.getSize();
        }
        Sort.Direction direction = Constant.DIRECTION;
        Sort sort = new Sort(direction, "companyScore", "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<EcContactEntity> ecContactEntities = ecContactManager.findAll(criteria, pageable);
        List<ContactVO> contactVOS = new ArrayList<>(ecContactEntities.getContent().size());
        for (EcContactEntity ecContactEntity : ecContactEntities) {
            ContactVO vo = new ContactVO();
            vo.setCompany(ecContactEntity.getCompany());
            vo.setId(ecContactEntity.getId());
            if (StrUtil.isEmpty(ecContactEntity.getMobile())) {
                vo.setMobile(ecContactEntity.getPhone());
            } else {
                vo.setMobile(ecContactEntity.getMobile());
            }
            vo.setName(ecContactEntity.getName());
            vo.setAddress(ecContactEntity.getAddress());
            vo.setVocation(ecVocationCodeManager.findNameByCode(ecContactEntity.getVocation()));
            vo.setProvince(ecCodeAreaManager.findById(ecContactEntity.getProvince() + ""));
            vo.setCompanyScore(CommonUtil.cutDouble2(ecContactEntity.getCompanyScore()));
            contactVOS.add(vo);
        }
        return new SimplePage<>(ecContactEntities.getTotalPages(), ecContactEntities.getTotalElements(), contactVOS);
    }

    /**
     * 查询每个省份下的数量
     *
     * @return
     * list
     */
    public ContactGroupVO findCountByProvince(Integer province, Integer city) {
        List<Map<String, Object>> list = new ArrayList<>();
        Long totalCount = 0L;

        //分别按省、市、行业分组
        if (province == null && city == null) {
            List<Object[]> objList = ecContactManager.findCountByProvince();
            for (Object[] objects : objList) {
                long count = CommonUtil.parseObject(objects[1]);
                if (count < PROVINCE_MIN_COUNT) {
                    continue;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("province", objects[0]);
                map.put(COUNT, objects[1]);
                map.put("name", ecCodeAreaManager.findById(objects[0].toString()));
                list.add(map);
                totalCount += count;
            }
        } else if (province != null && city == null) {
            //查到的包括市和县
            List<Object[]> objList = ecContactManager.findCountByCity(province);
            //将县合并到市里去，除了直辖市外
            Map<String, Long> map = new HashMap<>();
            for (Object[] objects : objList) {
                String key;
                //判断如果是非直辖市的县的话，合并到市里的数据里去
                Integer cityCode = (Integer) objects[0];
                if (CommonUtil.isZhiXiaShi(cityCode)) {
                    key = cityCode + "";
                } else if (cityCode == 0) {
                    key = "0";
                } else {
                    key = cityCode / 100 + "00";
                }
                if (map.get(key) == null) {
                    map.put(key, (Long) objects[1]);
                } else {
                    //将值相加
                    map.put(key, (map.get(key)) + (Long) objects[1]);
                }
            }
            //遍历map
            for (String key : map.keySet()) {
                long count = map.get(key);
                if (count < CITY_MIN_COUNT) {
                    continue;
                }
                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("city", key);
                hashMap.put(COUNT, count);
                hashMap.put("name", ecCodeAreaManager.findById(key));
                list.add(hashMap);
                totalCount += CommonUtil.parseObject(map.get(key));
            }

        } else {
            List<Object[]> objList = ecContactManager.findCountByVocation(city);
            Map<String, Long> map = new HashMap<>();
            for (Object[] objects : objList) {
                //判断如果是二级行业的话，合并的一级行业的数据里去
                Integer vocation = (Integer) objects[0];
                String key = vocation + "";
                //大于18的就是2级行业了
                if (vocation > 18) {
                    //获取它对应的1级行业
                    key = ecVocationCodeManager.findParentCode(vocation) + "";
                }
                if (map.get(key) != null) {
                    //将值相加
                    map.put(key, (map.get(key)) + (Long) objects[1]);
                } else {
                    map.put(key, (Long) objects[1]);
                }

            }
            //遍历map
            for (String key : map.keySet()) {
                long count = map.get(key);
                if (count < CITY_MIN_COUNT) {
                    continue;
                }
                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("vocation", key);
                hashMap.put(COUNT, count);
                hashMap.put("name", ecVocationCodeManager.findNameByCode(Integer.valueOf(key)));
                list.add(hashMap);
                totalCount += CommonUtil.parseObject(map.get(key));
            }
        }

        //按count进行排序
        return new ContactGroupVO(totalCount, list.stream().sorted((o1, o2) -> {
            if ((Long) o1.get(COUNT) > (Long) o2.get(COUNT)) {
                return -1;
            } else if ((Long) o1.get(COUNT) < (Long) o2.get(COUNT)) {
                return 1;
            }
            return 0;
        }).collect(Collectors.toList()));
    }

    /**
     * 查询某段时间每天的线索新增
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @return list结果集
     */
    public List<Map<String, Object>> findCountByDateBetween(String begin, String end) {
        Date beginTime = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endTime = DateUtil.endOfDay(DateUtil.parseDate(end));
        List<Map<String, Object>> list = new ArrayList<>();
        for (; beginTime.before(endTime); beginTime = DateUtil.offsetDay(beginTime, 1)) {
            //查一天的统计
            Date oneDayEnd = DateUtil.endOfDay(beginTime);
            Integer count = ecContactManager.countByCreateTimeBetween(beginTime, oneDayEnd);
            Map<String, Object> map = new HashMap<>(2);
            map.put("date", beginTime);
            map.put(COUNT, count);
            list.add(map);
        }
        return list;
    }

}
