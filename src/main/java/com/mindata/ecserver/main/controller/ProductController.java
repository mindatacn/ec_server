package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.model.secondary.PtProduct;
import com.mindata.ecserver.main.service.ProductService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hanliqiang wrote on 2018/1/18
 */
@RestController
@RequiresRoles(value = Constant.ROLE_ADMIN)
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductService productService;

    /**
     * 新增一个产品
     *
     * @param ptProduct ptProduct
     * @return result
     */
    @PostMapping("")
    public BaseData add(@ModelAttribute PtProduct ptProduct) {
        return ResultGenerator.genSuccessResult(productService.save(ptProduct));
    }

    /**
     * 更新一个产品
     *
     * @param ptProduct ptProduct
     * @return result
     */
    @PutMapping("")
    public BaseData update(@ModelAttribute PtProduct ptProduct) {
        if (ptProduct.getId() == null) {
            return ResultGenerator.genFailResult("产品id不能为空");
        }
        return ResultGenerator.genSuccessResult(productService.update(ptProduct));
    }

    /**
     * 删除一个产品
     *
     * @param id id
     * @return result
     */
    @DeleteMapping("/{id}")
    public BaseData delete(@PathVariable Long id) {
        Integer code = productService.delete(id);
        if (code == 1) {
            return ResultGenerator.genFailResult("还有公司在用，不允许删除");
        }
        return ResultGenerator.genSuccessResult("删除成功");
    }

    /**
     * 根据名称模糊查询
     *
     * @param name name
     * @return result
     */
    @GetMapping("")
    public BaseData find(String name) {
        return ResultGenerator.genSuccessResult(productService.findByName(name));
    }

}
