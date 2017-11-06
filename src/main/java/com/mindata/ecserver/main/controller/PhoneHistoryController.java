package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.manager.PtPhoneHistoryCompanyManager;
import com.mindata.ecserver.main.service.PhoneHistoryCompanyService;
import com.mindata.ecserver.main.service.PhoneHistoryDeptService;
import com.mindata.ecserver.main.service.PhoneHistoryUserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Resource
    private PtPhoneHistoryCompanyManager ptPhoneHistoryCompanyManager;

    /**
     * 根据传来的参数进行查询，查公司级的
     *
     * @return 公司级数据
     */
    @RequiresRoles(Constant.ROLE_MANAGER)
    @CheckEcAnnotation
    @GetMapping("/company")
    public BaseData queryCompany(Integer companyId, String begin, String end,
                                 @PageableDefault(direction =
                                         Sort.Direction.DESC, sort = "startTime") Pageable pageable) throws
            IOException {
        return ResultGenerator.genSuccessResult(phoneHistoryCompanyService.findHistoryByDate(companyId, begin, end,
                pageable));
    }

    /**
     * 查询部门的统计
     */
    @RequiresRoles(value = {Constant.ROLE_MANAGER, Constant.ROLE_LEADER}, logical = Logical.OR)
    @GetMapping("/dept")
    public BaseData queryUser(Integer deptId, String begin, String end,
                              @PageableDefault(direction =
                                      Sort.Direction.DESC, sort = "startTime") Pageable pageable) {
        return ResultGenerator.genSuccessResult(phoneHistoryDeptService.findDeptHistoryByDateBetween(deptId, begin,
                end, pageable));
    }

    /**
     * 查询个人的统计
     */
    @RequiresRoles(value = {Constant.ROLE_LEADER, Constant.ROLE_USER}, logical = Logical.OR)
    @GetMapping("/user")
    public BaseData query(Integer userId, String begin, String end,
                          @PageableDefault(direction =
                                  Sort.Direction.DESC, sort = "startTime") Pageable pageable) {
        return ResultGenerator.genSuccessResult(phoneHistoryUserService.findPersonalHistoryByDate(userId,
                begin, end,
                pageable));
    }

    /**
     * 查询所有用户某段时间任务完成情况，for HR
     */
    @RequestMapping("/state")
    public Object queryUserFinishState(String begin, String end) {
        return phoneHistoryCompanyService.findHistoryStrByDate(begin, end);
    }
}
