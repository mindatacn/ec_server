package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.response.CustomerGroupBean;
import com.mindata.ecserver.main.model.secondary.PtCustomerGroup;
import com.mindata.ecserver.main.repository.secondary.PtCustomerGroupRepository;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
@Service
public class PtCustomerGroupManager {
    @Resource
    private PtCustomerGroupRepository ptCustomerGroupRepository;

    /**
     * 向数据库添加员工客户库分组信息
     * @param customerGroupBean ec返回的结果
     * @return 数据库结果
     */
    public PtCustomerGroup add(CustomerGroupBean customerGroupBean){
        PtCustomerGroup ptCustomerGroup = ptCustomerGroupRepository.findByGroupId(customerGroupBean.getGroupId());
        if (ptCustomerGroup!=null){
            return ptCustomerGroup;
        }
        ptCustomerGroup = new PtCustomerGroup();
        ptCustomerGroup.setGroupId(customerGroupBean.getGroupId());
        ptCustomerGroup.setGroupName(customerGroupBean.getGroupName());
        ptCustomerGroup.setSort(customerGroupBean.getSort());
        ptCustomerGroup.setCreateTime(CommonUtil.getNow());
        ptCustomerGroup.setUpdateTime(CommonUtil.getNow());
        ptCustomerGroup = ptCustomerGroupRepository.save(ptCustomerGroup);
        return ptCustomerGroup;
    }
    public List<PtCustomerGroup> findAll(){
        return ptCustomerGroupRepository.findAll();
    }
}
