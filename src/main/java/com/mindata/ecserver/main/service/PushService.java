package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.request.CustomerCreateRequest;
import com.mindata.ecserver.ec.model.response.CustomerCreateData;
import com.mindata.ecserver.ec.model.response.CustomerCreateDataBean;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.CustomerService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.event.ContactPushResultEvent;
import com.mindata.ecserver.main.manager.EcCodeAreaManager;
import com.mindata.ecserver.main.manager.EcContactManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.requestbody.PushBody;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.PushResultCountVO;
import com.mindata.ecserver.main.vo.PushResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/31.
 * 推送联系人信息到ec
 */
@Service
public class PushService extends BaseService {
    @Resource
    private EcContactManager ecContactManager;
    @Resource
    private EcCodeAreaManager ecCodeAreaManager;
    @Resource
    private ServiceBuilder serviceBuilder;
    @Resource
    private CallManager callManager;
    @Resource
    private PtUserManager ptUserManager;


    /**
     * 将联系人信息导入到ec
     *
     * @param pushBody
     *         联系人的id集合
     */
    @Transactional(rollbackFor = Exception.class)
    public PushResultCountVO push(PushBody pushBody) throws IOException {
        List<EcContactEntity> contactEntities = ecContactManager.findByIds(pushBody.getIds());
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest();
        //设置操作人id
        customerCreateRequest.setOptUserId(ShiroKit.getCurrentUser().getEcUserId());
        //设置跟进人id
        customerCreateRequest.setFollowUserId(ptUserManager.findByUserId(pushBody.getFollowUserId().intValue())
                .getEcUserId());
        customerCreateRequest.setFieldNameMapping(fieldName());
        customerCreateRequest.setFieldValueList(fieldValueList(contactEntities));
        CustomerService customerService = serviceBuilder.getCustomerService();
        //得到返回值
        CustomerCreateData customerCreateData = (CustomerCreateData) callManager.execute(customerService.batchCreate
                (customerCreateRequest));
        //发布事件
        CustomerCreateDataBean bean = customerCreateData.getData();
        eventPublisher.publishEvent(new ContactPushResultEvent(new PushResultVO(bean,
                pushBody, ShiroKit.getCurrentUser().getId()
        )));

        int totalCount = pushBody.getIds().size();
        int failureCount = bean.getSuccessCrmIds().size();
        int successCount = totalCount - failureCount;
        return new PushResultCountVO(successCount, failureCount);
    }

    private Object[] fieldName() {
        List<String> fieldList = new ArrayList<>();
        fieldList.add("f_name");
        fieldList.add("f_mobile");
        fieldList.add("f_phone");
        fieldList.add("f_title");
        fieldList.add("f_fax");
        fieldList.add("f_qq");
        fieldList.add("f_email");
        fieldList.add("f_company");
        fieldList.add("f_company_addr");
        fieldList.add("f_company_url");
//      fieldList.add("f_call");
        fieldList.add("f_memo");
        fieldList.add("f_gender");
        fieldList.add("f_company_province");
        fieldList.add("f_company_city");
//      fieldList.add("f_vocation");
//      fieldList.add("f_channel");
        return fieldList.toArray();
    }

    private List<Object[]> fieldValueList(List<EcContactEntity> contactEntities) {
        List<Object[]> valueList = new ArrayList<>(contactEntities.size());
        for (EcContactEntity e : contactEntities) {
            List<Object> v = new ArrayList<>();
            v.add(e.getName());
            v.add(e.getMobile());
            v.add(e.getPhone());
            v.add(e.getTitle());
            v.add(e.getFax());
            v.add(e.getQq());
            v.add(e.getEmail());
            v.add(e.getCompany());
            v.add(e.getAddress());
            v.add(e.getUrl());
            v.add(e.getMemo());
            v.add(e.getGender() == null ? 0 : e.getGender());
            v.add(ecCodeAreaManager.findById(e.getProvince()));
            v.add(ecCodeAreaManager.findById(e.getCity()));
            valueList.add(v.toArray());
        }
        return valueList;
    }
}
