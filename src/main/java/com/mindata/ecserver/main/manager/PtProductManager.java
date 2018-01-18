package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtProduct;
import com.mindata.ecserver.main.repository.secondary.PtProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hanliqiang wrote on 2018/1/18
 */
@Service
public class PtProductManager {
    @Resource
    private PtProductRepository ptProductRepository;

    /**
     * 新增一个产品
     *
     * @param ptProduct ptProduct
     */
    public PtProduct save(PtProduct ptProduct) {
       return ptProductRepository.save(ptProduct);
    }

    /**
     * 根据id删除一个产品
     *
     * @param id id
     */
    public void delete(Long id) {
        ptProductRepository.delete(id);
    }

    /**
     * 根据名称模糊查询
     *
     * @param name name
     * @return List
     */
    public List<PtProduct> findByName(String name) {
        return ptProductRepository.findByNameLike("%" + name + "%");
    }

    /**
     * 根据id查询
     *
     * @param id id
     * @return PtProduct
     */
    public PtProduct findById(Long id) {
        return ptProductRepository.findOne(id);
    }
}
