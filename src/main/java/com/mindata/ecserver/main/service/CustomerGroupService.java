package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.request.CustomerGroupRequest;
import com.mindata.ecserver.ec.model.response.CustomerGroupData;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.CustomerGroupInfoService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.event.CompanySyncEvent;
import com.mindata.ecserver.main.manager.PtCustomerGroupManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtUser;
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
public class CustomerGroupService {
    @Resource
    private ServiceBuilder serviceBuilder;
    @Resource
    private CallManager callManager;
    @Resource
    private PtUserManager ptUserManager;
    @Resource
    private PtCustomerGroupManager ptCustomerGroupManager;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 把信息同步到db
     * 将从ec查出的员工客户库分组信息添加到数据库
     */
    @EventListener(CompanySyncEvent.class)
    public void syncFromEcToDb() throws IOException {
        logger.info("从EC获取客户分组信息");
        List<PtUser> userList = ptUserManager.findByCompanyIdAndState(ShiroKit.getCurrentUser().getCompanyId(),
                Constant.STATE_NORMAL);
        for (PtUser user : userList) {
            CustomerGroupRequest request = new CustomerGroupRequest();
            if (user.getEcUserId() == null) {
                continue;
            }
            request.setUserId(user.getEcUserId());
            CustomerGroupInfoService infoService = serviceBuilder.getCustomerGroupInfoService();
            CustomerGroupData groupData = (CustomerGroupData) callManager.execute(infoService
                    .getCustomerGroupInfo(request));
            logger.info("用户" + user.getId() + "的分组信息为：");
            logger.info(ptCustomerGroupManager.addAll(user.getId(), groupData.getData()).toString());
        }

    }

}
