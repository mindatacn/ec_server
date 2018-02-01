package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtSearchCondition;
import com.mindata.ecserver.main.repository.secondary.PtSearchConditionRepository;
import com.mindata.ecserver.main.requestbody.ContactRequestBody;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/11/21.
 */
@Service
public class PtSearchConditionManager {
    @Resource
    private PtSearchConditionRepository ptSearchConditionRepository;

    /**
     * 添加一条搜索记录
     *
     * @param contactRequestBody
     *         检索条件
     * @return 记录
     */
    public PtSearchCondition add(ContactRequestBody contactRequestBody, Long userId) {
        PtSearchCondition ptSearchCondition = new PtSearchCondition();
        BeanUtils.copyProperties(contactRequestBody, ptSearchCondition);
        if (contactRequestBody.getProvinces() != null) {
            ptSearchCondition.setProvinces(CollectionUtil.newHashSet(contactRequestBody.getProvinces()));
        }
        if (contactRequestBody.getCities() != null) {
            ptSearchCondition.setCities(CollectionUtil.newHashSet(contactRequestBody.getCities()));
        }
        //来源
        if (!CollectionUtil.isEmpty(contactRequestBody.getWebsiteIds())) {
            ptSearchCondition.setWebsiteIds(CollectionUtil.newHashSet(contactRequestBody.getWebsiteIds()));
        }
        //规模
        if (!CollectionUtil.isEmpty(contactRequestBody.getMemberSizeTags())) {
            ptSearchCondition.setMemberSizeTags(CollectionUtil.newHashSet(contactRequestBody.getMemberSizeTags()));
        }
        //行业
        if (!CollectionUtil.isEmpty(contactRequestBody.getVocations())) {
            ptSearchCondition.setVocations(CollectionUtil.newHashSet(contactRequestBody.getVocations()));
        }

        ptSearchCondition.setUserId(userId);
        ptSearchCondition.setCreateTime(CommonUtil.getNow());
        ptSearchCondition.setUpdateTime(CommonUtil.getNow());
        return ptSearchConditionRepository.save(ptSearchCondition);
    }
}
