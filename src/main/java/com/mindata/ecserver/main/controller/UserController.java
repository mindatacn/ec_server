package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.service.UserService;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

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

    /**
     * 绑定EC账号
     *
     * @param account
     *         ec的手机号
     * @return 结果
     */
    @PostMapping("/bindEc")
    @CheckEcAnnotation
    public BaseData bindEc(String account) throws IOException {
        if (!CommonUtil.isMobile(account)) {
            return ResultGenerator.genFailResult("手机号格式不正确");
        }
        int result = userService.bindEc(account);
        if (result < 0) {
            return ResultGenerator.genFailResult("该账号已被其他用户绑定");
        }
        return ResultGenerator.genSuccessResult("绑定成功");
    }

    /**
     * 修改个人信息
     */
    @PutMapping
    public BaseData modifyInfo(String name, String mobile, String email) {
        return ResultGenerator.genSuccessResult(userService.modifyInfo(name, mobile, email));
    }
}
