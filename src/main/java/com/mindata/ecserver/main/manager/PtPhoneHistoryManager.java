package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.request.PhoneHistoryRequest;
import com.mindata.ecserver.ec.model.response.PhoneHistory;
import com.mindata.ecserver.ec.model.response.PhoneHistoryData;
import com.mindata.ecserver.ec.model.response.PhoneHistoryDataBean;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.main.model.secondary.PtPhoneHistory;
import com.mindata.ecserver.main.repository.secondary.PtPhoneHistoryRepository;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author wuweifeng wrote on 2017/11/3.
 */
@Service
public class PtPhoneHistoryManager {
    @Resource
    private PtPhoneHistoryRepository ptPhoneHistoryRepository;
    @Resource
    private ServiceBuilder serviceBuilder;
    @Resource
    private CallManager callManager;
    @Resource
    private PtPushResultManager ptPushResultManager;
    @Resource
    private PtUserManager ptUserManager;

    private List<PhoneHistoryDataBean> historyDataBeans = new ArrayList<>();
    private int nowPageNo;
    private int maxPageNo;

    /**
     * 查询某用户某天的通话统计信息
     * @param userId
     *         userId
     * @return
     */
    public List<Object[]> findTotalByUserIdAndOneDay(Integer userId, Date tempBegin, Date tempEnd) throws IOException {
        Long ecUserId = ptUserManager.findByUserId(userId).getEcUserId();
        //没绑定ec
        if (ecUserId == null) {
            //返回全为0
            return generEmptyList();
        }

        List<PtPhoneHistory> histories = ptPhoneHistoryRepository.findByEcUserIdAndRealRecodeFalseAndStartTimeBetween
                (ecUserId, tempBegin, tempEnd);
        //如果该天的是假数据，就直接返回回去
        if (histories.size() > 0) {
            //返回全为0
            return generEmptyList();
        }

        List<Object[]> list = ptPhoneHistoryRepository.findCount(ecUserId, tempBegin, tempEnd);
        //如果该天数据缺失
        if (list.get(0)[0].equals(0L)) {
            //说明该天该用户缺失，就从EC获取一次
            historyDataBeans.clear();
            nowPageNo = 1;
            maxPageNo = 10000;
            getFromEc(ecUserId, tempBegin);

            //如果EC也没该用户的数据，我们就造一条
            if (CollectionUtil.isEmpty(historyDataBeans)) {
                PtPhoneHistory ptPhoneHistory = new PtPhoneHistory();
                ptPhoneHistory.setEcUserId(ecUserId);
                ptPhoneHistory.setStartTime(tempBegin);
                ptPhoneHistory.setRealRecode(false);
                ptPhoneHistory.setCreateTime(CommonUtil.getNow());
                ptPhoneHistory.setUpdateTime(CommonUtil.getNow());
                save(ptPhoneHistory);

                //返回全为0
                return generEmptyList();
            }

            //将从EC取得的数据导入数据库
            return intoDB();

        }
        //只有3个字段
        Object[] tempObjects = list.get(0);
        //如果该天有真实数据
        List<PtPhoneHistory> ptPhoneHistories = ptPhoneHistoryRepository.findByEcUserIdAndStartTimeBetween(ecUserId,
                tempBegin, tempEnd);
        int pushCount = 0;
        int validCount = 0;
        for (PtPhoneHistory history : ptPhoneHistories) {
            //判断crmId是否在我们成功推送的列表里，如果是，那就是该数据是我们推送的
            boolean crmExist = ptPushResultManager.existCrmId(history.getCrmId());
            if (crmExist) {
                pushCount++;
            }
            if (history.getCallTime() > 0) {
                validCount++;
            }
        }
        Object[] objects = new Object[5];
        objects[0] = tempObjects[0];
        objects[1] = tempObjects[1];
        objects[2] = tempObjects[2];
        objects[3] = pushCount;
        objects[4] = validCount;
        list.clear();
        list.add(objects);
        return list;
    }

    /**
     * 将ec回来的历史数据入库
     */
    private List<Object[]> intoDB() {
        List<Object[]> list = new ArrayList<>();
        Object[] objects = new Object[5];
        objects[0] = historyDataBeans.size();
        //排除重复的联系人
        Set<String> customerSet = new HashSet<>();
        int totalCallTime = 0;
        int pushCount = 0, validCount = 0;

        for (PhoneHistoryDataBean bean : historyDataBeans) {
            PtPhoneHistory ptPhoneHistory = new PtPhoneHistory();
            ptPhoneHistory.setCallTime(Integer.valueOf(bean.getCalltime()));
            ptPhoneHistory.setCallToNo(bean.getCalltono());
            ptPhoneHistory.setCrmId(bean.getCrmId());
            ptPhoneHistory.setCustomerCompany(bean.getCustomerCompany());
            ptPhoneHistory.setCustomerName(bean.getCustomerName());
            ptPhoneHistory.setEcUserId(bean.getUserId());
            ptPhoneHistory.setType(bean.getType());
            ptPhoneHistory.setMd5(bean.getMd5());
            ptPhoneHistory.setPath(bean.getPath());
            ptPhoneHistory.setStartTime(DateUtil.parseTime(bean.getStarttime()));
            ptPhoneHistory.setRealRecode(true);
            ptPhoneHistory.setCreateTime(CommonUtil.getNow());
            ptPhoneHistory.setUpdateTime(CommonUtil.getNow());

            totalCallTime += Integer.valueOf(bean.getCalltime());
            //判断crmId是否在我们成功推送的列表里，如果是，那就是该数据是我们推送的
            boolean crmExist = ptPushResultManager.existCrmId(bean.getCrmId());
            if (crmExist) {
                pushCount++;
            }
            if (ptPhoneHistory.getCallTime() > 0) {
                validCount++;
            }

            customerSet.add(bean.getCalltono());
            save(ptPhoneHistory);
        }

        objects[1] = totalCallTime;
        objects[2] = customerSet.size();
        objects[3] = pushCount;
        objects[4] = validCount;
        list.add(objects);
        return list;
    }

    private void getFromEc(Long ecUserId, Date oneDay) throws IOException {
        if (nowPageNo > maxPageNo) {
            return;
        }

        PhoneHistoryRequest request = new PhoneHistoryRequest();
        String date = DateUtil.formatDate(oneDay);
        request.setStartDate(date);
        request.setEndDate(date);
        request.setUserIds(ecUserId + "");
        request.setPageNo(nowPageNo);
        PhoneHistory phoneHistory = (PhoneHistory) callManager.execute(serviceBuilder.getPhoneHistoryService().history
                (request));
        PhoneHistoryData data = phoneHistory.getData();
        if (data == null) {
            return;
        }

        historyDataBeans.addAll(data.getResult());

        maxPageNo = data.getMaxPageNo();
        nowPageNo++;
        getFromEc(ecUserId, oneDay);
    }

    private List<Object[]> generEmptyList() {
        List<Object[]> list = new ArrayList<>();
        Object[] objects = new Object[]{0, 0, 0, 0, 0};
        list.add(objects);
        return list;
    }


    public List<PtPhoneHistory> findByEcUserIdAndDateBetween(Long ecUserId, Date begin, Date end) {
        return null;
    }

    public PtPhoneHistory save(PtPhoneHistory ptPhoneHistory) {
        return ptPhoneHistoryRepository.save(ptPhoneHistory);
    }
}
