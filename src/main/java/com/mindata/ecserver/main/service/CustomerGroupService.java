package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.request.CustomerGroupRequest;
import com.mindata.ecserver.ec.model.response.CustomerGroupBean;
import com.mindata.ecserver.ec.model.response.CustomerGroupData;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.CustomerGroupInfoService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.event.CompanySyncEvent;
import com.mindata.ecserver.main.manager.PtCustomerGroupManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtUser;
import org.springframework.context.event.EventListener;
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
    @Resource
    private PtUserManager ptUserManager;
    @Resource
    private PtCustomerGroupManager ptCustomerGroupManager;

    /**
     * @return 将从ec查出的员工客户库分组信息添加到数据库
     * @throws IOException
     */
    @EventListener(CompanySyncEvent.class)
    public void syncFromEcToDb() throws IOException {
        List<PtUser> userList = ptUserManager.findByCompanyIdAndState(ShiroKit.getCurrentUser().getCompanyId(), 0);
        for (PtUser user : userList) {
            CustomerGroupRequest request = new CustomerGroupRequest();
            if (user.getEcUserId() != null) {
                request.setUserId(user.getEcUserId().toString());
                CustomerGroupInfoService infoService = serviceBuilder.getCustomerGroupInfoService();
                CustomerGroupData groupData = (CustomerGroupData) callManager.execute(infoService.getCustomerGroupInfo(request));
                for (CustomerGroupBean customerGroupBean : groupData.getData()) {
                    ptCustomerGroupManager.add(customerGroupBean);
                }
            }
        }
    }

}
