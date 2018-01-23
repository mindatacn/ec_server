package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.request.CustomerCreateRequest;
import com.mindata.ecserver.ec.model.response.CustomerCreateData;
import com.mindata.ecserver.ec.model.response.CustomerCreateDataBean;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.CustomerService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.global.bean.ResultCode;
import com.mindata.ecserver.global.shiro.ShiroKit;
import com.mindata.ecserver.main.event.ContactPushResultEvent;
import com.mindata.ecserver.main.manager.PtCompanyManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.manager.PtUserPushCountManager;
import com.mindata.ecserver.main.manager.ec.EcCodeAreaManager;
import com.mindata.ecserver.main.manager.ec.EcContactManager;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.model.secondary.PtUserPushCount;
import com.mindata.ecserver.main.requestbody.PushBody;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.PushResultCountVO;
import com.mindata.ecserver.main.vo.PushResultVO;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Resource
    private PtUserPushCountManager ptUserPushCountManager;
    @Resource
    private PtCompanyManager ptCompanyManager;

    private static final int MAX_SIZE = 50;

    /**
     * 推送前校验
     *
     * @param pushBody
     *         pushBody
     * @return 能否推送的结果
     */
    public Map<String, Object> pushCheck(PushBody pushBody) {

        PtUser ptUser = ShiroKit.getCurrentUser();
        //没传跟进人，默认为自己
        if (pushBody.getFollowUserId() == null) {
            if (ptUser.getEcUserId() == null) {
                return buildMap(ResultCode.FAIL, "用户没绑定EC账号");
            }
            pushBody.setFollowUserId(ptUser.getId());
        }
        List<Long> ids = pushBody.getIds();
        if (ids.size() > MAX_SIZE) {
            return buildMap(ResultCode.PUSH_COUNT_TO_LARGE, "用户没绑定EC账号");
        }
        //检查被推送的用户每日上限
        PtUserPushCount userCount = ptUserPushCountManager.findCountByUserId(pushBody.getFollowUserId(), null);
        if (ids.size() + userCount.getPushedCount() > userCount.getThreshold()) {
            Integer surplusCount = userCount.getThreshold() - userCount.getPushedCount();
            return buildMap(ResultCode.PUSH_COUNT_BEYOND_TODAY_LIMIT, "今日推送额度剩余【" +
                    surplusCount
                    + "】条");
        }
        // 检查已推送的数量是否大于公司规定的推送数量
        Long companyId = ShiroKit.getCurrentUser().getCompanyId();
        Integer companyThreshold = ptCompanyManager.findOne(companyId).getThreshold();
        Integer pushedCount = ptUserPushCountManager.getPushedCountSum(companyId);
        if (ids.size() + pushedCount > companyThreshold) {
            Integer surplusCount = companyThreshold - pushedCount;
            return buildMap(ResultCode.PUSH_COUNT_BEYOND_TODAY_LIMIT, "公司今日推送额度剩余【" + surplusCount + "】条");
        }
        return buildMap(ResultCode.SUCCESS, "");
    }

    private Map<String, Object> buildMap(ResultCode resultCode, String msg) {
        Map<String, Object> map = CollectionUtil.newHashMap(3);
        map.put("resultCode", resultCode);
        map.put("msg", msg);
        return map;
    }


    /**
     * 将联系人信息导入到ec
     *
     * @param pushBody
     *         联系人的id集合
     */
    @Transactional(rollbackFor = Exception.class)
    public PushResultCountVO push(PushBody pushBody) throws IOException {
        //对id进行排序
        List<Long> ids = pushBody.getIds();
        List<Long> newIds = ids.stream().sorted().collect(Collectors.toList());
        pushBody.setIds(newIds);

        List<EcContactEntity> contactEntities = ecContactManager.findByIds(pushBody.getIds());
        if (contactEntities.size() == 0) {
            return new PushResultCountVO(0, 0);
        }
        Long followUserId = ptUserManager.findByUserId(pushBody.getFollowUserId())
                .getEcUserId();
        Long optUserId;
        if (pushBody.getOptUserId() == null) {
            //设置操作人id
            optUserId = ShiroKit.getCurrentUser().getEcUserId();
        } else {
            optUserId = ptUserManager.findByUserId(pushBody.getOptUserId())
                    .getEcUserId();
        }

        CustomerCreateData customerCreateData = pushToEc(optUserId, followUserId, contactEntities);
        //发布事件
        CustomerCreateDataBean bean = customerCreateData.getData();
        eventPublisher.publishEvent(new ContactPushResultEvent(new PushResultVO(bean,
                pushBody, ShiroKit.getCurrentUser().getId()
        )));

        int totalCount = pushBody.getIds().size();
        int successCount = bean.getSuccessCrmIds().size();
        int failureCount = totalCount - successCount;
        return new PushResultCountVO(successCount, failureCount);
    }

    /**
     * 批量创建客户
     */
    private CustomerCreateData pushToEc(Long optUserId, Long followUserId, List<EcContactEntity> contactEntities)
            throws IOException {
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest();
        //设置跟进人id
        customerCreateRequest.setFollowUserId(followUserId);
        customerCreateRequest.setOptUserId(optUserId);
        customerCreateRequest.setFieldNameMapping(fieldName());
        customerCreateRequest.setFieldValueList(fieldValueList(contactEntities));
        CustomerService customerService = serviceBuilder.getCustomerService();
        //得到返回值，如果是全部失败，则CustomerCreateData里面的为null
        return (CustomerCreateData) callManager.execute(customerService.batchCreate
                (customerCreateRequest));
    }

    private Object[] fieldName() {
        List<String> fieldList = new ArrayList<>();
        fieldList.add("f_name");
        fieldList.add("f_mobile");
        fieldList.add("f_phone");
        fieldList.add("f_title");
        //fieldList.add("f_fax");
        //fieldList.add("f_qq");
        fieldList.add("f_email");
        fieldList.add("f_company");
        fieldList.add("f_company_addr");
        fieldList.add("f_company_url");
        fieldList.add("f_memo");
        fieldList.add("f_gender");
        fieldList.add("f_company_province");
        fieldList.add("f_company_city");
        fieldList.add("f_company_region");
//      fieldList.add("f_vocation");
        //TODO 如果是别的公司，则该channel需要修改
        //fieldList.add("f_channel");
        return fieldList.toArray();
    }

    private List<Object[]> fieldValueList(List<EcContactEntity> contactEntities) {
        List<Object[]> valueList = new ArrayList<>(contactEntities.size());
        for (EcContactEntity e : contactEntities) {
            List<Object> v = new ArrayList<>();
            if (StrUtil.isEmpty(e.getName())) {
                v.add("未知");
            } else {
                v.add(e.getName());
            }

            v.add(e.getMobile());
            v.add(e.getPhone() == null ? "" : e.getPhone());
            v.add(e.getTitle() == null ? "" : e.getTitle());
            //v.add(e.getFax());
            //v.add(e.getQq());
            v.add(e.getEmail() == null ? "" : e.getEmail());
            v.add(e.getCompany() == null ? "" : e.getCompany());
            v.add(e.getAddress() == null ? "" : e.getAddress());
            v.add(e.getUrl() == null ? "" : e.getUrl());
            v.add(e.getMemo() == null ? "" : e.getMemo());
            v.add(e.getGender() == null ? 0 : e.getGender());
            //判断是否是直辖市
            if (CommonUtil.isZhiXiaShi(e.getProvince())) {
                //省为空
                v.add("");
                v.add(ecCodeAreaManager.findById(e.getProvince() + ""));
                v.add(ecCodeAreaManager.findById(e.getCity() + ""));
            } else {
                v.add(ecCodeAreaManager.findById(e.getProvince() + ""));
                v.add(ecCodeAreaManager.findById(e.getCity() + ""));
                v.add("");
            }
            //v.add("82014661");
            valueList.add(v.toArray());
        }
        return valueList;
    }
}
