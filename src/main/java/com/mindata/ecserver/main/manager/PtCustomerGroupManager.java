package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.response.CustomerGroupBean;
import com.mindata.ecserver.main.model.secondary.PtCustomerGroup;
import com.mindata.ecserver.main.repository.secondary.PtCustomerGroupRepository;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
@Service
public class PtCustomerGroupManager {
    @Resource
    private PtCustomerGroupRepository ptCustomerGroupRepository;

    /**
     * 向数据库添加员工客户库分组信息
     *
     * @param customerGroupBean
     *         ec返回的结果
     * @return 数据库结果
     */
    public PtCustomerGroup add(Long userId, CustomerGroupBean customerGroupBean, boolean force) {
        PtCustomerGroup ptCustomerGroup = ptCustomerGroupRepository.findByGroupId(customerGroupBean.getGroupId());
        if (ptCustomerGroup != null && !force) {
            return ptCustomerGroup;
        }
        if (ptCustomerGroup == null) {
            ptCustomerGroup = new PtCustomerGroup();
        }
        ptCustomerGroup.setUserId(userId);
        ptCustomerGroup.setGroupId(customerGroupBean.getGroupId());
        ptCustomerGroup.setGroupName(customerGroupBean.getGroupName());
        ptCustomerGroup.setSort(customerGroupBean.getSort());
        ptCustomerGroup.setCreateTime(CommonUtil.getNow());
        ptCustomerGroup.setUpdateTime(CommonUtil.getNow());
        ptCustomerGroup = ptCustomerGroupRepository.save(ptCustomerGroup);
        return ptCustomerGroup;
    }

    /**
     * 添加一批数据
     *
     * @param customerGroupBeans
     *         一批
     * @return 结果
     */
    public List<PtCustomerGroup> addAll(Long userId, List<CustomerGroupBean> customerGroupBeans, boolean force) {
        return customerGroupBeans.stream().map(customerGroupBean -> add(userId, customerGroupBean, force)).collect
                (Collectors.toList());
    }

    /**
     * 查询某个用户的分组集合
     *
     * @param userId
     *         用户id
     * @return 集合
     */
    public List<PtCustomerGroup> findByUserId(Long userId) {
        return ptCustomerGroupRepository.findByUserId(userId);
    }
}