package com.mindata.ecserver.main.service;

import com.mindata.ecserver.main.manager.PtCompanyManager;
import com.mindata.ecserver.main.manager.PtProductManager;
import com.mindata.ecserver.main.model.secondary.PtProduct;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.BeanUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hanliqiang wrote on 2018/1/18
 */
@Service
public class ProductService {
    @Resource
    private PtProductManager ptProductManager;
    @Resource
    private PtCompanyManager ptCompanyManager;

    /**
     * 新增一个产品
     *
     * @param ptProduct ptProduct
     */
    public PtProduct save(PtProduct ptProduct) {
        ptProduct.setCreateTime(CommonUtil.getNow());
        ptProduct.setUpdateTime(CommonUtil.getNow());
        return ptProductManager.save(ptProduct);
    }

    /**
     * 删除一个产品
     *
     * @param id id
     * @return Integer
     */
    public Integer delete(Long id) {
        Integer count = ptCompanyManager.countByProductId(id);
        if (count > 0) {
            return 1;
        }
        ptProductManager.delete(id);
        return 0;
    }

    /**
     * 修改产品
     *
     * @param ptProduct ptProduct
     */
    public PtProduct update(PtProduct ptProduct) {
        PtProduct product = ptProductManager.findById(ptProduct.getId());
        product.setUpdateTime(CommonUtil.getNow());
        BeanUtil.copyProperties(ptProduct, product, BeanUtil.CopyOptions.create().setIgnoreNullValue(true));
        return ptProductManager.save(product);
    }

    /**
     * 根据名称模糊查询
     *
     * @param name name
     * @return List
     */
    public List<PtProduct> findByName(String name) {
        if (StrUtil.isEmpty(name)) {
            return ptProductManager.findAll();
        }
        return ptProductManager.findByName(name);
    }

}
