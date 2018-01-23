package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtCompany;
import com.mindata.ecserver.main.repository.secondary.PtCompanyRepository;
import com.mindata.ecserver.main.requestbody.CompanyBody;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
        // 初次购买
        ptCompany.setBuyStatus(1);
        ptCompany.setStatus(0);
        ptCompany.setCreateTime(now);
        ptCompany.setUpdateTime(now);
        BeanUtils.copyProperties(companyBody, ptCompany);
        return companyRepository.save(ptCompany);
    }

    /**
     * 修改
     * @param ptCompany ptCompany
     * @return PtCompany
     */
    public PtCompany update(PtCompany ptCompany) {
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

    /**
     * 查询所有公司的阈值
     */
    public List<PtCompany> findThreshold(String name){
        if(StrUtil.isEmpty(name)){
            return companyRepository.findAll();
        }
        return companyRepository.findByNameLike("%" + name + "%");
    }

    /**
     * 根据id修改阈值
     * @param id id
     * @param threshold threshold
     * @return PtCompany
     */
    public PtCompany updateThresholdById(Long id,Integer threshold){
        PtCompany ptCompany = companyRepository.findOne(id);
        ptCompany.setThreshold(threshold);
        return companyRepository.save(ptCompany);
    }
    /**
     * 分页查找
     * @param var1 var1
     * @param var2 var2
     * @return  Page
     */
    public Page<PtCompany> findAll(Specification<PtCompany> var1, Pageable var2) {
        return companyRepository.findAll(var1, var2);
    }

    public List<PtCompany> findPtCompanyByBuyStatus(){
        return companyRepository.findByBuyStatusNot(4);
    }
}
