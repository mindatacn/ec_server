package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.ec.model.response.CustomerTagDataBean;
import com.mindata.ecserver.main.model.secondary.PtCustomerTag;
import com.mindata.ecserver.main.repository.secondary.PtCustomerTagRepository;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hanliqiang wrote on 2017/11/15
 */
@Service
public class PtCustomerTagManager {
    @Resource
    private PtCustomerTagRepository ptCustomerTagRepository;

    /**
     * 向数据库添加标签信息
     *
     * @param customerTagDataBean ec返回的结果
     * @return 数据库结果
     */
    public PtCustomerTag add(CustomerTagDataBean customerTagDataBean) {
        PtCustomerTag ptCustomerTag = ptCustomerTagRepository.findByClassId(customerTagDataBean.getClassId());
        if (ptCustomerTag != null) {
            return ptCustomerTag;
        }
        ptCustomerTag = new PtCustomerTag();
        ptCustomerTag.setClassId(customerTagDataBean.getClassId());
        ptCustomerTag.setClassName(customerTagDataBean.getClassName());
        ptCustomerTag.setSort(customerTagDataBean.getSort());
        ptCustomerTag.setCreateTime(CommonUtil.getNow());
        ptCustomerTag.setUpdateTime(CommonUtil.getNow());
        ptCustomerTag = ptCustomerTagRepository.save(ptCustomerTag);
        return ptCustomerTag;
    }
}
