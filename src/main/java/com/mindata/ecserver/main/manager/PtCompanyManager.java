package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtCompany;
import com.mindata.ecserver.main.repository.secondary.PtCompanyRepository;
import com.mindata.ecserver.main.requestbody.CompanyBody;
import com.mindata.ecserver.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class PtCompanyManager {
    @Resource
    private PtCompanyRepository companyRepository;

    /**
     * 新增一个Company
     *
     * @param companyBody
     *         body
     * @return Company
     */
    public PtCompany add(CompanyBody companyBody) {
        Date now = CommonUtil.getNow();
        PtCompany ptCompany = new PtCompany();
        ptCompany.setStatus(0);
        ptCompany.setCreateTime(now);
        ptCompany.setUpdateTime(now);
        BeanUtils.copyProperties(companyBody, ptCompany);
        return companyRepository.save(ptCompany);
    }

    public PtCompany findOne(Integer id) {
        return companyRepository.findOne(id);
    }
}
