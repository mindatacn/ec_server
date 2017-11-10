package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.global.specify.Criteria;
import com.mindata.ecserver.global.specify.Restrictions;
import com.mindata.ecserver.main.manager.EcVocationCodeManager;
import com.mindata.ecserver.main.manager.es.EsContactManager;
import com.mindata.ecserver.main.model.es.EsContact;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.repository.primary.EcContactRepository;
import com.mindata.ecserver.main.requestbody.ContactRequestBody;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.ContactVO;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 * 条件查询联系人数据（未推送的）
 */
@Service
public class ContactService extends BaseService {
    @Resource
    private EcContactRepository contactRepository;
    @Resource
    private EsContactManager esContactManager;
    @Resource
    private EcVocationCodeManager ecVocationCodeManager;

    public EcContactEntity findById(int id) {
        EsContact esContact = esContactManager.findById((long) id);

        EcContactEntity ecContactEntity = contactRepository.findOne(id);
        if (esContact != null) {
            ecContactEntity.setMemo(esContact.getComintro());
        }

        return ecContactEntity;
    }

    public SimplePage<ContactVO> findByStateAndConditions(int state, ContactRequestBody
            contactRequestBody) {
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
            criteria.add(Restrictions.ne("mobile", "", true));
        }
        //招聘信息
        if (contactRequestBody.getNeedSale() != null && contactRequestBody.getNeedSale()) {
            criteria.add(Restrictions.eq("needSale", true, true));
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
        if (contactRequestBody.getOrder() != null && contactRequestBody.getOrder()) {
            direction = Sort.Direction.ASC;
        }
        String orderBy = "createTime";
        if (!StrUtil.isEmpty(contactRequestBody.getOrderBy())) {
            orderBy = contactRequestBody.getOrderBy();
        }
        Pageable pageable = new PageRequest(page, size, direction, orderBy);
        Page<EcContactEntity> ecContactEntities = contactRepository.findAll(criteria, pageable);
        List<ContactVO> contactVOS = new ArrayList<>(ecContactEntities.getContent().size());
        for (EcContactEntity ecContactEntity : ecContactEntities) {
            ContactVO vo = new ContactVO();
            vo.setCompany(ecContactEntity.getCompany());
            vo.setId(ecContactEntity.getId());
            vo.setMobile(ecContactEntity.getMobile());
            vo.setName(ecContactEntity.getName());
            vo.setVocation(ecVocationCodeManager.findNameByCode(ecContactEntity.getVocation()));
            vo.setProvince(ecCodeAreaManager.findById(ecContactEntity.getProvince()));
            contactVOS.add(vo);
        }
        return new SimplePage<>(ecContactEntities.getTotalPages(), ecContactEntities.getTotalElements(), contactVOS);
    }

}
