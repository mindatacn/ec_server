package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.requestbody.OrderBody;
import com.mindata.ecserver.main.service.OrderService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.mindata.ecserver.global.constant.Constant.ROLE_ADMIN;

/**
 * @author hanliqiang wrote on 2018/1/23
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 新增一条订单
     *
     * @return BaseData
     */
    @PostMapping("")
    public BaseData add(@RequestBody OrderBody orderBody) {
        return ResultGenerator.genSuccessResult(orderService.add(orderBody));
    }

    @GetMapping("")
    public BaseData query(Long companyId) {
        return ResultGenerator.genSuccessResult(orderService.findByCompanyId(companyId));
    }

    /**
     * 修改一条订单
     *
     * @param orderBody
     *         orderBody
     * @return BaseData
     */
    @PutMapping("")
    @RequiresRoles(ROLE_ADMIN)
    public BaseData update(@ModelAttribute OrderBody orderBody) {
        if (orderBody.getId() == null) {
            return ResultGenerator.genFailResult("订单id不能为空");
        }
        return ResultGenerator.genSuccessResult(orderService.update(orderBody));
    }

    /**
     * 删除一条订单
     *
     * @param id
     *         id
     * @return BaseData
     */
    @DeleteMapping("/{id}")
    @RequiresRoles(ROLE_ADMIN)
    public BaseData delete(@PathVariable Long id) {
        boolean flag = orderService.delete(id);
        if (flag) {
            return ResultGenerator.genSuccessResult("删除成功");
        } else {
            return ResultGenerator.genFailResult("只有一条记录时，不允许删除");
        }
    }
}
                                     