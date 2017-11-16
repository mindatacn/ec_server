package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.request.CustomerTagRequest;
import com.mindata.ecserver.ec.model.response.CustomerTagData;
import com.mindata.ecserver.ec.model.response.CustomerTagDataBean;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.CustomerTagInfoService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.main.event.CompanySyncEvent;
import com.mindata.ecserver.main.manager.PtCustomerTagGroupManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
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
    private CallManager callManager;
    @Resource
    private PtCustomerTagGroupManager ptCustomerTagGroupManager;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 将标签同步到数据库
     */
    @EventListener(CompanySyncEvent.class)
    public void syncFromEcToDb(CompanySyncEvent companySyncEvent) throws IOException {
        logger.info("从EC获取客户标签信息");
        CustomerTagRequest customerTagRequest = new CustomerTagRequest();
        customerTagRequest.setGroupValue("");
        CustomerTagInfoService customerTagInfoService = serviceBuilder.getClassInfoService();
        CustomerTagData customerTagData = (CustomerTagData) callManager.execute(customerTagInfoService
                .getLabelInfo(customerTagRequest));
        List<CustomerTagDataBean> customerTagDataBeans = customerTagData.getData();
        logger.info("从EC获取客户标签信息如下：");
        ptCustomerTagGroupManager.addAll(customerTagDataBeans, (Boolean) companySyncEvent.getSource());
    }
}
