package com.mindata.ecserver.main.service;

import com.mindata.ecserver.ec.model.request.ClassRequest;
import com.mindata.ecserver.ec.model.response.ClassData;
import com.mindata.ecserver.ec.model.response.ClassDataBean;
import com.mindata.ecserver.ec.retrofit.ServiceBuilder;
import com.mindata.ecserver.ec.service.ClassInfoService;
import com.mindata.ecserver.ec.util.CallManager;
import com.mindata.ecserver.main.manager.PtCustomerGroupManager;
import com.mindata.ecserver.main.model.secondary.PtCustomerGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
@Service
public class ClassService {
    @Resource
    private ServiceBuilder serviceBuilder;
    @Resource
    private PtCustomerGroupManager ptCustomerGroupManager;
    @Resource
    private CallManager callManager;

    public List<ClassDataBean> getLabelInfo() throws IOException {
        List<PtCustomerGroup> groupList = ptCustomerGroupManager.findAll();
        for (PtCustomerGroup ptCustomerGroup:groupList){
            ClassRequest classRequest = new ClassRequest();
            classRequest.setGroupValue(ptCustomerGroup.getGroupId());
            ClassInfoService classInfoService =  serviceBuilder.getClassInfoService();
            ClassData classData = (ClassData) callManager.execute(classInfoService.getLabelInfo(classRequest));
            classData.getClassBeanList();
        }
        return null;
    }
}
