package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.response.CustomerOneTagBean;
import com.mindata.ecserver.ec.model.response.CustomerTagDataBean;
import com.mindata.ecserver.main.model.secondary.PtCustomerTagGroup;
import com.mindata.ecserver.main.repository.secondary.PtCustomerTagGroupRepository;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/11/15
 */
@Service
public class PtCustomerTagGroupManager {
    @Resource
    private PtCustomerTagGroupRepository ptCustomerTagGroupRepository;
    @Resource
    private PtCustomerTagManager ptCustomerTagManager;

    /**
     * 向数据库添加客户标签分组信息
     *
     * @param customerTagDataBean
     *         ec返回的结果
     * @param force
     *         是否强制更新
     * @return 数据库结果
     */
    public PtCustomerTagGroup add(CustomerTagDataBean customerTagDataBean, boolean force) {
        PtCustomerTagGroup ptCustomerGroup = ptCustomerTagGroupRepository.findByGroupId(customerTagDataBean
                .getGroupId());
        if (ptCustomerGroup != null && !force) {
            return ptCustomerGroup;
        }
        if (ptCustomerGroup == null) {
            ptCustomerGroup = new PtCustomerTagGroup();
        }
        //添加标签分组信息
        ptCustomerGroup.setGroupId(customerTagDataBean.getGroupId());
        ptCustomerGroup.setGroupName(customerTagDataBean.getGroupName());
        ptCustomerGroup.setSort(customerTagDataBean.getSort());
        ptCustomerGroup.setType(customerTagDataBean.getType());
        ptCustomerGroup.setCreateTime(CommonUtil.getNow());
        ptCustomerGroup.setUpdateTime(CommonUtil.getNow());
        ptCustomerGroup = ptCustomerTagGroupRepository.save(ptCustomerGroup);

        //添加单条标签信息
        List<CustomerOneTagBean> customerOneTagBeans = customerTagDataBean.getList();
        ptCustomerTagManager.addAll(customerTagDataBean.getGroupId(), customerTagDataBean.getGroupName(),
                customerOneTagBeans, force);

        return ptCustomerGroup;
    }

    /**
     * 添加一批数据
     *
     * @param customerTagDataBeans
     *         一批
     * @return 结果
     */
    public List<PtCustomerTagGroup> addAll(List<CustomerTagDataBean> customerTagDataBeans, boolean force) {
        return customerTagDataBeans.stream().map(customerTagDataBean -> add(customerTagDataBean, force)).collect
                (Collectors.toList());
    }
}
