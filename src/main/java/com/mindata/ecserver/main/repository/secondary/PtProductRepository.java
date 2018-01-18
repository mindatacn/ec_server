package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hanliqiang wrote on 2018/1/18
 */
public interface PtProductRepository extends JpaRepository<PtProduct, Long> {
    /**
     * 根据名称模糊查询
     * @param name
     * name
     * @return
     * list
     */
    List<PtProduct> findByNameLike(String name);
}
