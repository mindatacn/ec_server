package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.service.UserService;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.mindata.ecserver.global.bean.ResultCode.PARAMETER_ERROR;

/**
 * @author wuweifeng wrote on 2017/11/2.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PutMapping("/modifyPassword")
    public BaseData modifyPass(String oldPassword, String newPassword) {
        if (StrUtil.isEmpty(oldPassword) || StrUtil.isEmpty(oldPassword) || newPassword.length() < 6) {
            return ResultGenerator.genFailResult(PARAMETER_ERROR, "参数错误");
        }
        int code = userService.modifyPassword(oldPassword, newPassword);
        if (code == -1) {
            return ResultGenerator.genFailResult(PARAMETER_ERROR, "老密码错误");
        }
        return ResultGenerator.genSuccessResult("修改成功");
    }
}
