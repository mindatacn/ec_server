package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.response.CustomerOneTagBean;
import com.mindata.ecserver.main.model.secondary.PtCustomerTag;
import com.mindata.ecserver.main.repository.secondary.PtCustomerTagRepository;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
@Service
public class PtCustomerTagManager {
    @Resource
    private PtCustomerTagRepository ptCustomerTagRepository;

    /**
     * 向数据库添加标签信息
     *
     * @param customerOneTagBean ec返回的结果
     * @return 数据库结果
     */
    public PtCustomerTag add(Long groupId, String groupName, CustomerOneTagBean customerOneTagBean, boolean force) {
        PtCustomerTag ptCustomerTag = ptCustomerTagRepository.findByClassId(customerOneTagBean.getClassId());
        if (ptCustomerTag != null && !force) {
            return ptCustomerTag;
        }
        if (ptCustomerTag == null) {
            ptCustomerTag = new PtCustomerTag();
        }
        ptCustomerTag.setClassId(customerOneTagBean.getClassId());
        ptCustomerTag.setClassName(customerOneTagBean.getClassName());
        ptCustomerTag.setSort(customerOneTagBean.getSort());
        ptCustomerTag.setGroupId(groupId);
        ptCustomerTag.setGroupName(groupName);
        ptCustomerTag.setCreateTime(CommonUtil.getNow());
        ptCustomerTag.setUpdateTime(CommonUtil.getNow());
        ptCustomerTag = ptCustomerTagRepository.save(ptCustomerTag);
        return ptCustomerTag;
    }

    public List<PtCustomerTag> addAll(Long groupId, String groupName, List<CustomerOneTagBean>
            customerOneTagBeans, boolean force) {
        return customerOneTagBeans.stream().map(customerOneTagBean -> add(groupId, groupName,
                customerOneTagBean, force))
                .collect(Collectors.toList());
    }
}
