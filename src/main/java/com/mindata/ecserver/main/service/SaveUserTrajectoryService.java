package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.base.BaseEcData;
import com.mindata.ecserver.ec.model.request.UserTrajector;
import com.mindata.ecserver.ec.model.request.UserTrajectoryRequest;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.UserTrajectoryService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.main.manager.PtPushResultManager;
import com.mindata.ecserver.main.model.secondary.PtPushSuccessResult;
import com.xiaoleilu.hutool.io.file.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入跟进历史
 *
 * @author wuweifeng wrote on 2017/11/28.
 */
@Service
public class SaveUserTrajectoryService {
    @Resource
    private ServiceBuilder serviceBuilder;
    @Resource
    private CallManager callManager;
    @Resource
    private PtPushResultManager ptPushResultManager;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 读取Excel信息（客户跟进记录）导入到EC
     *
     * @param path
     *         Excel路径
     */
    public void userTrajectoryToEc(String path) {
        logger.info("开始读取Excel表格");
        FileReader fileReader = new FileReader(path);
        List<String> list = fileReader.readLines();

        List<UserTrajector> userTrajectors = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            logger.info("开始解析");
            userTrajectors.add(readLine(list.get(i), i));
            if ((i + 1) % 50 == 0) {
                logger.info("开始插入EC，当前是第" + i + "个");
                try {
                    //调用EC接口
                    saveUserTrajectory(userTrajectors);
                    userTrajectors.clear();
                } catch (Exception e) {
                    logger.info("插入EC异常，当前是第" + i + "个");
                }

            }
        }
        if (userTrajectors.size() > 0) {
            try {
                //调用EC接口
                saveUserTrajectory(userTrajectors);
            } catch (Exception e) {
                logger.info("插入EC异常，当前是最后一组");
            }
        }
        logger.info("插入完毕");
    }

    /**
     * 解析一行
     *
     * @param rowStr
     *         一行的字符串
     * @param rowNum
     *         行号
     * @return UserTrajector
     */
    private UserTrajector readLine(String rowStr, int rowNum) {
        String[] array = rowStr.split(",");
        //生成id
        String id = array[1] + rowNum;
        PtPushSuccessResult ptPushSuccessResult = ptPushResultManager.findResultByMobile(array[3]);
        if (ptPushSuccessResult == null) {
            return null;
        }
        Long crmId = ptPushSuccessResult.getCrmId();
        Long userId = ptPushSuccessResult.getFollowUserId();
        UserTrajector userTrajector = new UserTrajector();
        userTrajector.setContactTime(array[5]);
        userTrajector.setContent(array[6]);
        userTrajector.setCrmId(crmId);
        userTrajector.setUserId(userId);
        userTrajector.setId(id);
        return userTrajector;
    }

    /**
     * 添加客户跟进记录
     */
    private BaseEcData saveUserTrajectory(List<UserTrajector> userTrajectors) throws IOException {
        UserTrajectoryRequest trajectoryRequest = new UserTrajectoryRequest();
        trajectoryRequest.setList(userTrajectors);
        UserTrajectoryService userTrajectorService = serviceBuilder.getUserTrajectoryService();
        //得到返回值
        return callManager.execute(userTrajectorService.batchCreate
                (trajectoryRequest));
    }
}
