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
    @Resource
    private PtProductManager ptProductManager;

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

    /**
     * 根据corpId查询公司
     *
     * @param corpId
     *         corpId
     * @return 公司
     */
    public PtCompany findByEcCorpId(Long corpId) {
        return companyRepository.findByCorpId(corpId);
    }

    public PtCompany findOne(Long id) {
        return companyRepository.findOne(id);
    }

    /**
     * 根据productId 查询
     * @param productId productId
     * @return Integer
     */
    public Integer countByProductId(Long productId){
        return companyRepository.countByProductId(productId);
    }

    /**
     * 判断该公司状态是否异常(status不为0，或者对应的product的state为-1)
     *
     * @param companyId
     *         公司id
     * @return 是否异常
     */
    public boolean isStatusError(Long companyId) {
        PtCompany ptCompany = companyRepository.findOne(companyId);
        return ptCompany != null && (ptCompany.getStatus() != 0 || ptProductManager.isError(ptCompany.getProductId()));
    }

}
