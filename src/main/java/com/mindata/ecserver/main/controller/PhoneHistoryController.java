package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.service.PhoneHistoryCompanyService;
import com.mindata.ecserver.main.service.PhoneHistoryDeptService;
import com.mindata.ecserver.main.service.PhoneHistoryUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author wuweifeng wrote on 2017/11/5.
 * 营销历史统计，公司、部门、员工某段时间的累计打电话历史
 */
@RestController
@RequestMapping("/historyStatis")
public class PhoneHistoryController {
    @Resource
    private PhoneHistoryCompanyService phoneHistoryCompanyService;
    @Resource
    private PhoneHistoryUserService phoneHistoryUserService;
    @Resource
    private PhoneHistoryDeptService phoneHistoryDeptService;

    /**
     * 获取所有历史数据，由定时器定时调用该接口
     */
    @CheckEcAnnotation
    @GetMapping("/fetch")
    public BaseData fetchCompanyData(Long companyId, String begin, String end, Boolean force,
                                     @PageableDefault(direction =
                                             Sort.Direction.DESC, sort = "startTime") Pageable pageable) throws
            IOException {
        if (force == null) {
            force = false;
        }
        return ResultGenerator.genSuccessResult(phoneHistoryCompanyService.fetchAllHistoryData(companyId, begin, end,
                pageable, force));
    }

    /**
     * 根据传来的参数进行查询，查公司级的
     *
     * @return 公司级数据
     */
    @GetMapping("/company")
    public BaseData queryCompany(Long companyId, String begin, String end,
                                 @PageableDefault(direction =
                                         Sort.Direction.DESC, sort = "startTime") Pageable pageable) throws
            IOException {
        return ResultGenerator.genSuccessResult(phoneHistoryCompanyService.findHistoryByDate(companyId, begin, end));
    }

    /**
     * 查询部门的统计累计数据，只有一条
     */
    //@RequiresRoles(value = {Constant.ROLE_MANAGER, Constant.ROLE_LEADER}, logical = Logical.OR)
    @GetMapping("/dept/{id}")
    public BaseData queryDept(@PathVariable Long id, String begin, String end,
                              @PageableDefault(direction =
                                      Sort.Direction.DESC, sort = "startTime") Pageable pageable) {
        return ResultGenerator.genSuccessResult(phoneHistoryDeptService.findDeptHistoryByDateBetween(id, begin,
                end, pageable));
    }

    /**
     * 查询所有部门的统计，按部门分开
     */
    //@RequiresRoles(value = {Constant.ROLE_MANAGER, Constant.ROLE_LEADER}, logical = Logical.OR)
    @GetMapping("/dept")
    public BaseData queryUser(Long companyId, String begin, String end,
                              @PageableDefault(direction =
                                      Sort.Direction.DESC, sort = "startTime") Pageable pageable) {
        return ResultGenerator.genSuccessResult(phoneHistoryDeptService.findDeptHisTotalByCompanyIdAndDateBetween
                (companyId, begin,
                        end, pageable));
    }

    /**
     * 查询某部门所有人的统计，或者查询某公司所有人的统计
     */
    //@RequiresRoles(value = {Constant.ROLE_MANAGER, Constant.ROLE_LEADER, Constant.ROLE_USER}, logical = Logical.OR)
    @GetMapping("/user")
    public BaseData query(Long deptId, String begin, String end) {
        return ResultGenerator.genSuccessResult(phoneHistoryUserService.findPersonalHistoryByDate(deptId,
                begin, end));
    }

    @GetMapping("/user/{id}")
    public BaseData queryOneUser(@PathVariable Long id, String begin, String end) {
        return ResultGenerator.genSuccessResult(phoneHistoryUserService.findPersonalHistoryByUserId(id,
                begin, end));
    }

    /**
     * 查询所有用户某段时间任务完成情况，for HR
     */
    @RequestMapping("/state")
    public Object queryUserFinishState(String begin, String end) {
        return phoneHistoryCompanyService.findHistoryStrByDate(begin, end);
    }
}
