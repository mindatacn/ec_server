package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.service.PhoneHistoryCompanyService;
import com.mindata.ecserver.main.service.PhoneHistoryUserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

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

    /**
     * 根据传来的参数进行查询，如果什么都不传，就是查公司级的，传
     *
     * @return 公司级数据
     */
    @RequiresRoles(Constant.ROLE_MANAGER)
    @CheckEcAnnotation
    @GetMapping("/company")
    public BaseData queryCompany(String begin, String end,
                                 @PageableDefault(direction =
                                         Sort.Direction.DESC, sort = "startTime") Pageable pageable) throws
            IOException {
        return ResultGenerator.genSuccessResult(phoneHistoryCompanyService.findHistoryByDate(null, begin, end,
                pageable));
    }

    @RequiresRoles({Constant.ROLE_MANAGER, Constant.ROLE_LEADER})
    @GetMapping("/dept/{ids}")
    public BaseData queryDept(@PathVariable String ids, Date begin, Date end,
                              @PageableDefault(direction =
                                      Sort.Direction.DESC, sort = "startTime") Pageable pageable) {

        return null;
    }

    @RequiresRoles({Constant.ROLE_MANAGER, Constant.ROLE_LEADER, Constant.ROLE_USER})
    @GetMapping("/user/{ids}")
    public BaseData queryUser(@PathVariable String ids, Date begin, Date end,
                              @PageableDefault(direction =
                                      Sort.Direction.DESC, sort = "startTime") Pageable pageable) {
        return null;
    }

    /**
     * 查询个人的统计
     */
    @RequiresRoles(Constant.ROLE_USER)
    @RequestMapping("")
    public BaseData query(String begin, String end,
                          @PageableDefault(direction =
                                  Sort.Direction.DESC, sort = "startTime") Pageable pageable) {
        phoneHistoryUserService.findPersonalHistoryByDate(begin, end, pageable);
        return null;
    }

    /**
     * 查询所有用户某段时间任务完成情况
     *
     * @param begin
     * @param end
     * @return
     */
    @RequestMapping("/state")
    public Object queryUserFinishState(String begin, String end) {
        return phoneHistoryCompanyService.findHistoryStrByDate(begin, end);
    }
}
