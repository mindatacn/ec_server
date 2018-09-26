package com.mindata.ecserver;

import com.mindata.ecserver.main.manager.CodeAreaManager;
import com.mindata.ecserver.main.repository.primary.CodeAreaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EcServerApplicationTests {
    @Resource
    private CodeAreaManager codeAreaManager;
    @Resource
    private CodeAreaRepository codeAreaRepository;

    @Test
    public void contextLoads() {
        //System.out.println(codeAreaManager.findCitiesByProvince("110000"));
        //System.out.println(codeAreaRepository.findAll());
    }

}
