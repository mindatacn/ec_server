package com.mindata.ecserver;

import com.mindata.ecserver.main.manager.EcCodeAreaManager;
import com.mindata.ecserver.main.manager.EcVocationCodeManager;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.repository.primary.CodeAreaRepository;
import com.mindata.ecserver.main.repository.secondary.PtPhoneHistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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
    private PtUserManager ptUserManager;

    @Test
    public void contextLoads() {
        //System.out.println(codeAreaManager.findCitiesByProvince("110000"));
        //System.out.println(codeAreaRepository.findAll());
        //System.out.println(ecVocationCodeManager.findAll());
        //Pageable pageable = new PageRequest(0, 10);
        //Date date = new Date();
        //Date tempBegin = DateUtil.beginOfDay(date);
        //Date tempEnd = DateUtil.endOfDay(date);
        //List<Object[]> list = ptPhoneHistoryRepository.findCount(1L, tempBegin, tempEnd);
        //Object[] objects = list.get(0);
        //
        //System.out.println(objects[0].equals(0L));
    }

}
