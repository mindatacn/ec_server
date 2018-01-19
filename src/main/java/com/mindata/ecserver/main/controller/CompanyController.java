package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.requestbody.CompanyBody;
import com.mindata.ecserver.main.service.CompanyService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

import static com.mindata.ecserver.global.constant.Constant.ROLE_ADMIN;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Resource
    private CompanyService companyService;

    /**
     * 添加一家新的公司
     *
     * @param company 公司各信息
     * @return 结果
     */
    @PostMapping({"", "/"})
    @RequiresRoles(ROLE_ADMIN)
    public BaseData add(@RequestBody CompanyBody company) {
        return ResultGenerator.genSuccessResult(companyService.addCompany(company));
    }

    /**
     * 从EC同步部门和人员信息到本系统
     *
     * @return 结果
     */
    @GetMapping("/sync")
    @CheckEcAnnotation
    public BaseData sync(Boolean force) throws IOException {
        return ResultGenerator.genSuccessResult(companyService.syncFromEc(force));
    }

    /**
     * 查询公司的阈值
     *
     * @param name name
     * @return result
     */
    @GetMapping("/threshold")
    public BaseData findThreshold(String name) {
        return ResultGenerator.genSuccessResult(companyService.findThreshold(name));
    }

    /**
     * 根据id修改阈值
     *
     * @param id        id
     * @param threshold threshold
     * @return result
     */
    @PutMapping("")
    @RequiresRoles(ROLE_ADMIN)
    public BaseData updateThresholdById(Long id, Integer threshold) {
        if (id == null) {
            return ResultGenerator.genFailResult("公司id不能为空");
        }
        return ResultGenerator.genSuccessResult(companyService.updateThresholdById(id, threshold));
    }
}
