package com.mindata.ecserver;

import com.mindata.ecserver.main.manager.*;
import com.mindata.ecserver.main.manager.ec.EcCodeAreaManager;
import com.mindata.ecserver.main.manager.ec.EcContactManager;
import com.mindata.ecserver.main.manager.ec.EcVocationCodeManager;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.mindata.ecserver.main.repository.primary.CodeAreaRepository;
import com.mindata.ecserver.main.repository.primary.EcContactRepository;
import com.mindata.ecserver.main.repository.secondary.PtPhoneHistoryRepository;
import com.mindata.ecserver.main.requestbody.PushBody;
import com.mindata.ecserver.main.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EcServerApplicationTests {
    @Resource
    private EcCodeAreaManager ecCodeAreaManager;
    @Resource
    private CodeAreaRepository codeAreaRepository;
    @Resource
    private EcVocationCodeManager ecVocationCodeManager;
    @Resource
    private PtPhoneHistoryRepository ptPhoneHistoryRepository;
    @Resource
    private PhoneHistoryCompanyService phoneHistoryCompanyService;
    @Resource
    private PtUserManager ptUserManager;
    @Resource
    private PtSearchConditionManager ptSearchConditionManager;
    @Resource
    private PhoneHistoryUserService phoneHistoryUserService;
    @Resource
    private EcContactManager ecContactManager;
    @Resource
    private PushService pushService;
    @Resource
    private CompanyService companyService;
    @Resource
    private EcContactRepository ecContactRepository;
    @Resource
    private SaveUserTrajectoryService saveUserTrajectoryService;


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void contextLoads() throws IOException {
        //excelToTrajectory();
        //List<PtSearchCondition> conditions = ptSearchConditionManager.find();
        //System.out.println(codeAreaManager.findCitiesByProvince("110000"));
        //System.out.println(codeAreaRepository.findAll());
        //System.out.println(ecVocationCodeManager.findAll());
        //Pageable pageable = new PageRequest(0, 10);
        //Date date = new Date();
        //Date tempBegin = DateUtil.beginOfDay(date);
        //Date tempEnd = DateUtil.endOfDay(date);
        //List<Object[]> list = ptPhoneHistoryRepository.findCount(1L, tempBegin, tempEnd);
        //Object[] objects = list.get(0);
        //List<PtUser> ptUsers = ptUserManager.findAll();
        //for (PtUser ptUser : ptUsers) {
        //     Long ecUserId = ptUser.getEcUserId();
        //     if (ecUserId == null) {
        //         continue;
        //     }
        //     String pass = CommonUtil.password(ecUserId + "");
        //     ptUser.setPassword(pass);
        //    ptUserManager.update(ptUser);
        //}
        //
        //System.out.println(objects[0].equals(0L));
        //phoneHistoryCompanyService.fetchAllHistoryData(1L, "2017-11-03", "2017-11-15", new PageRequest(0, 10));
    }

    private void excelToTrajectory() {
        //同步ec用户数据，需要用controller来调用
        //companyService.syncFromEc(false);
        String path = "";
        //导入数据到Contact表
        ecContactManager.excelToContact(path);

        //查询该公司所有员工
        List<PtUser> ptUsers = ptUserManager.findByCompanyIdAndState(2L, 0);
        //依次查询该公司单个员工的所有跟进客户
        for (PtUser ptUser : ptUsers) {
            logger.info("当前员工为" + ptUser.getName());
            //查询Contact表，开始往ec推送
            Pageable pageable = new PageRequest(0, 50, Sort.Direction.ASC, "id");
            Page<EcContactEntity> ecContactEntities = ecContactRepository.findByFax(ptUser.getName(), pageable);
            int totalPage = ecContactEntities.getTotalPages();

            logger.info("当前员工共有客户" + ecContactEntities.getTotalElements() + "个");
            if (totalPage == 0) {
                continue;
            }
            logger.info("开始导入" + ptUser.getName() + "的客户数据到EC");

            for (int i = 0; i < totalPage; i++) {
                logger.info("第" + i + "页");
                pageable = new PageRequest(i, 50, Sort.Direction.ASC, "id");
                ecContactEntities = ecContactRepository.findByFax(ptUser.getName(),
                        pageable);
                List<Long> ids = ecContactEntities.getContent().stream().map(EcContactEntity::getId).collect
                        (Collectors.toList());
                //开始推送到EC
                PushBody pushBody = new PushBody();
                pushBody.setFollowUserId(ptUser.getEcUserId());
                pushBody.setOptUserId(ptUser.getEcUserId());
                pushBody.setIds(ids);
                logger.info("开始往EC插入第" + i + "页的客户数据");
                try {
                    pushService.push(pushBody);
                } catch (Exception e) {
                    logger.info("插入数据抛异常了，继续下一页");
                    continue;
                }
                logger.info("插入第" + i + "页数据完毕");
            }
            logger.info(ptUser.getName() + "的客户数据导入到EC完毕");
        }

        //开始导入跟进记录
        saveUserTrajectoryService.userTrajectoryToEc(path);
    }

}
