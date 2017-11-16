package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.request.CustomerTagRequest;
import com.mindata.ecserver.ec.model.response.CustomerTagData;
import com.mindata.ecserver.ec.model.response.CustomerTagDataBean;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.CustomerTagInfoService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.main.manager.PtCustomerGroupManager;
import com.mindata.ecserver.main.manager.PtCustomerTagManager;
import com.mindata.ecserver.main.model.secondary.PtCustomerGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
@Service
public class CustomerTagService {
    @Resource
    private ServiceBuilder serviceBuilder;
    @Resource
    private PtCustomerGroupManager ptCustomerGroupManager;
    @Resource
    private CallManager callManager;
    @Resource
    private PtCustomerTagManager ptCustomerTagManager;

    /**
     * 将标签同步到数据库
     *
     * @throws IOException
     */
    //@EventListener(CompanySyncEvent.class)
    public void syncFromEcToDb() throws IOException {
        List<PtCustomerGroup> groupList = ptCustomerGroupManager.findAll();
        for (PtCustomerGroup ptCustomerGroup : groupList) {
            CustomerTagRequest customerTagRequest = new CustomerTagRequest();
            customerTagRequest.setGroupValue(ptCustomerGroup.getGroupId());
            CustomerTagInfoService customerTagInfoService = serviceBuilder.getClassInfoService();
            CustomerTagData customerTagData = (CustomerTagData) callManager.execute(customerTagInfoService.getLabelInfo(customerTagRequest));
            for (CustomerTagDataBean customerTagDataBean : customerTagData.getClassBeanList()) {
                ptCustomerTagManager.add(customerTagDataBean);
            }
        }
    }
}
