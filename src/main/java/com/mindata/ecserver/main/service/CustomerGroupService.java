package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.request.CustomerGroupRequest;
import com.mindata.ecserver.ec.model.response.CustomerGroupBean;
import com.mindata.ecserver.ec.model.response.CustomerGroupData;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.CustomerGroupInfoService;
import com.mindata.ecserver.ec.util.CallManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
@Service
public class CustomerGroupService {
    @Resource
    private ServiceBuilder serviceBuilder;
    @Resource
    private CallManager callManager;

    /**
     *
     * @param customerGroupRequest 请求参数
     * @return 获取员工客户库分组信息
     * @throws IOException
     */
    public List<CustomerGroupBean> getCustomerGroup(CustomerGroupRequest customerGroupRequest) throws IOException {
        CustomerGroupInfoService infoService = serviceBuilder.getCustomerGroupInfoService();
        CustomerGroupData groupData = (CustomerGroupData) callManager.execute(infoService.getCustomerGroupInfo(customerGroupRequest));
        return groupData.getData();
    }
}
