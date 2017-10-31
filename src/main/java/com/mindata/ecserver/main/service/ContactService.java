package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.global.specify.Criteria;
import com.mindata.ecserver.global.specify.Restrictions;
import com.mindata.ecserver.main.manager.CodeAreaManager;
import com.mindata.ecserver.main.model.EcContactEntity;
import com.mindata.ecserver.main.repository.EcContactRepository;
import com.mindata.ecserver.main.requestbody.ContactRequestBody;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 * 条件查询联系人数据（未推送的）
 */
@Service
public class ContactService {
    @Resource
    private EcContactRepository contactRepository;
    @Resource
    private CodeAreaManager codeAreaManager;

    public EcContactEntity findById(int id) {
        return contactRepository.findOne(id);
    }

    public SimplePage<EcContactEntity> findByPushedAndConditions(boolean pushed, ContactRequestBody
            contactRequestBody) {
        Criteria<EcContactEntity> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("pushed", pushed, true));
        //有手机号
        if (contactRequestBody.getHasMobile() != null && contactRequestBody.getHasMobile()) {
            criteria.add(Restrictions.ne("mobile", "", true));
        }
        //招聘信息
        if (contactRequestBody.getNeedSale() != null && contactRequestBody.getNeedSale()) {
            criteria.add(Restrictions.eq("needSale", true, true));
        }
        //来源
        if (contactRequestBody.getWebsiteIds() != null) {
            criteria.add(Restrictions.in("websiteId", contactRequestBody.getWebsiteIds(), true));
        }
        //规模
        if (contactRequestBody.getMemberSizeTags() != null) {
            criteria.add(Restrictions.in("memberSizeTag", contactRequestBody.getMemberSizeTags(), true));
        }
        //行业
        if (contactRequestBody.getVocationTags() != null) {
            criteria.add(Restrictions.in("vocationTag", contactRequestBody.getVocationTags(), true));
        }
        //区域
        if (contactRequestBody.getProvinces() != null) {
            criteria.add(Restrictions.in("province", contactRequestBody.getProvinces(), true));
            List<String> cities = contactRequestBody.getCities();

            //如果勾了多个市，则用in
            if (cities != null) {
                //如果勾了某省，但是没勾它对应的市，则默认选中它所有的市
                for (String province : contactRequestBody.getProvinces()) {
                    if (!checkCityInProvice(cities, province)) {
                        cities.addAll(codeAreaManager.findCitiesByProvince(province));
                    }
                }
                criteria.add(Restrictions.in("city", contactRequestBody.getCities(), true));
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
        if (contactRequestBody.getOrder() != null && contactRequestBody.getOrder()) {
            direction = Sort.Direction.ASC;
        }
        String orderBy = "createTime";
        if (contactRequestBody.getOrderBy() != null) {
            orderBy = contactRequestBody.getOrderBy();
        }
        Pageable pageable = new PageRequest(page, size, direction, orderBy);
        return new SimplePage<>(contactRepository.findAll(criteria, pageable));
    }


    /**
     * 判断城市列表里是否包含该省
     *
     * @param cities
     *         城市集合
     * @param province
     *         省
     * @return 是否包含
     */
    private boolean checkCityInProvice(List<String> cities, String province) {
        String provinceHead = province.substring(0, 2);
        for (String city : cities) {
            if (city.substring(0, 2).equals(provinceHead)) {
                return true;
            }
        }
        return false;
    }
}
