package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.request.PhoneFarHistoryRequest;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public static final Integer ARRAY_SIZE = 9;

    private List<PhoneHistoryDataBean> historyDataBeans = new ArrayList<>();
    private int nowPageNo;
    private int maxPageNo;

    /**
     * 查询某客户的累计通话时长
     *
     * @param crmId
     *         客户id
     * @return 时间累计
     */
    public Integer findTotalContactTimeByCrmId(Long crmId) {
        return ptPhoneHistoryRepository.findTotalContactTimeByCrmId(crmId);
    }

    /**
     * 查最后一次通话记录
     *
     * @param crmId
     *         客户id
     * @return 时间
     */
    public Date findByCrmIdOrderByCallTime(Long crmId) {
        Pageable pageable = new PageRequest(0, 1);
        List<PtPhoneHistory> ptPhoneHistory = ptPhoneHistoryRepository.findByCrmIdOrderByCallTimeDesc(crmId, pageable);
        if (ptPhoneHistory.size() == 0) {
            return null;
        }
        return ptPhoneHistory.get(0).getStartTime();
    }

    /**
     * 查询某用户某天的通话统计信息
     *
     * @param userId
     *         userId
     * @return
     * 统计信息
     */
    public List<Object[]> findTotalByUserIdAndOneDay(Long userId, Date tempBegin, Date tempEnd, boolean force) throws
            IOException {
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
            //此处判断日期是否大于当前超过1个月，选择不同的方法请求数据
            long days = (CommonUtil.getNow().getTime() - tempBegin.getTime()) / (1000 * 3600 * 24);
            if (days > 30) {
                getFarFromEc(ecUserId, tempBegin);
            } else {
                getFromEc(ecUserId, tempBegin);
            }

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
        //排除重复的是我们推送的联系人
        Set<String> pushCustomerSet = new HashSet<>();
        int pushCount = 0, noPushCount = 0, pushCallTime = 0, pushValidCount = 0, validCount = 0;
        for (PtPhoneHistory history : ptPhoneHistories) {
            //判断crmId是否在我们成功推送的列表里，如果是，那就是该数据是我们推送的
            boolean crmExist = ptPushResultManager.existCrmId(history.getCrmId());
            if (crmExist) {
                //我们推送的沟通次数加1
                pushCount++;
                //我们推送的沟通有效加1
                if (history.getCallTime() > 0) {
                    pushValidCount++;
                    pushCallTime += history.getCallTime();
                }
                pushCustomerSet.add(history.getCallToNo());
            } else {
                noPushCount++;
            }
            if (history.getCallTime() > 0) {
                validCount++;
            }
        }
        Object[] objects = new Object[ARRAY_SIZE];
        objects[0] = tempObjects[0];
        objects[1] = tempObjects[1];
        objects[2] = tempObjects[2];
        objects[3] = pushCount;
        objects[4] = validCount;
        objects[5] = noPushCount;
        objects[6] = pushCallTime;
        objects[7] = pushCustomerSet.size();
        objects[8] = pushValidCount;

        list.clear();
        list.add(objects);
        return list;
    }

    /**
     * 将ec回来的历史数据入库
     */
    private List<Object[]> intoDB() {
        List<Object[]> list = new ArrayList<>();
        Object[] objects = new Object[ARRAY_SIZE];
        objects[0] = historyDataBeans.size();
        //排除重复的总的联系人
        Set<String> customerSet = new HashSet<>();
        //排除重复的是我们推送的联系人
        Set<String> pushCustomerSet = new HashSet<>();
        int totalCallTime = 0;
        int pushCount = 0, validCount = 0, noPushCount = 0, pushCallTime = 0, pushValidCount = 0;

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
            ptPhoneHistory.setStartTime(DateUtil.parse(bean.getStarttime()));
            ptPhoneHistory.setRealRecode(true);
            ptPhoneHistory.setCreateTime(CommonUtil.getNow());
            ptPhoneHistory.setUpdateTime(CommonUtil.getNow());

            totalCallTime += Integer.valueOf(bean.getCalltime());
            //判断crmId是否在我们成功推送的列表里，如果是，那就是该数据是我们推送的
            boolean crmExist = ptPushResultManager.existCrmId(bean.getCrmId());
            if (crmExist) {
                //未排重的我们的沟通的次数
                pushCount++;
                if (ptPhoneHistory.getCallTime() > 0) {
                    pushCallTime += ptPhoneHistory.getCallTime();
                    pushValidCount++;
                }
                pushCustomerSet.add(bean.getCalltono());
            } else {
                noPushCount++;
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
        objects[5] = noPushCount;
        objects[6] = pushCallTime;
        objects[7] = pushCustomerSet.size();
        objects[8] = pushValidCount;
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

    /**
     * 获取更早期的历史数据
     */
    private void getFarFromEc(Long ecUserId, Date oneDay) throws IOException {
        if (nowPageNo > maxPageNo) {
            return;
        }

        PhoneFarHistoryRequest request = new PhoneFarHistoryRequest();
        //2017-03-05
        String date = DateUtil.formatDate(oneDay);
        String[] array = date.split("-");
        request.setYear(Integer.valueOf(array[0]));
        request.setMonth(Integer.valueOf(array[1]));
        request.setStartDayOfMonth(Integer.valueOf(array[2]));
        request.setEndDayOfMonth(Integer.valueOf(array[2]));
        request.setUserIds(ecUserId + "");
        request.setPageNo(nowPageNo);
        PhoneHistory phoneHistory = (PhoneHistory) callManager.execute(serviceBuilder.getPhoneHistoryService()
                .farHistory(request));
        PhoneHistoryData data = phoneHistory.getData();
        if (data == null) {
            return;
        }

        historyDataBeans.addAll(data.getResult());

        maxPageNo = data.getMaxPageNo();
        nowPageNo++;
        getFarFromEc(ecUserId, oneDay);
    }

    private List<Object[]> generEmptyList() {
        List<Object[]> list = new ArrayList<>();
        Object[] objects = new Integer[ARRAY_SIZE];
        list.add(objects);
        return list;
    }

    public PtPhoneHistory save(PtPhoneHistory ptPhoneHistory) {
        return ptPhoneHistoryRepository.save(ptPhoneHistory);
    }
}
