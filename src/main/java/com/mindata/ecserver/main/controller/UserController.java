package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.annotation.CheckEcAnnotation;
import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.requestbody.PtUserRequestBody;
import com.mindata.ecserver.main.service.UserService;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

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
     * @param account ec的手机号
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

    /**
     * 获取个人信息
     */
    @GetMapping("/info")
    public BaseData userInfo() {
        return ResultGenerator.genSuccessResult(userService.getInfo());
    }

    /**
     * 根据名字模糊查询
     *
     * @param name 名字
     * @return 集合
     */
    @RequiresRoles(value = {Constant.ROLE_MANAGER, Constant.ROLE_LEADER, Constant.ROLE_USER}, logical = Logical.OR)
    @GetMapping("")
    public BaseData findUserByNameLike(String name) {
        return ResultGenerator.genSuccessResult(userService.findByNameLike(name));
    }

    /**
     * 获取自己的推送数量
     */
    @RequiresRoles(value = {Constant.ROLE_LEADER, Constant.ROLE_USER}, logical = Logical.OR)
    @GetMapping("/pushCount")
    public BaseData queryThreshold() {
        return ResultGenerator.genSuccessResult(userService.findPushCount());
    }

    /**
     * 获取自己的角色
     */
    @GetMapping("/role")
    public BaseData queryRole() {
        return ResultGenerator.genSuccessResult(userService.findRole());
    }

    /**
     * 查询同步后新添加的用户信息
     */
    @GetMapping("/idBetween")
    public BaseData findNewlyUsers(PtUserRequestBody requestBody) {
        return ResultGenerator.genSuccessResult(userService.findByIdBetween(requestBody));
    }

    /**
     * 修改用户推送的阈值
     *
     * @param userId    用户Id
     * @param threshold 推送的阈值
     * @return 结果
     */
    @PutMapping("/threshold")
    public BaseData updateThresholdByUserId(Long userId, Integer threshold) {
        Integer code = userService.updateThresholdByUserId(userId, threshold);
        if (code == 0) {
            return ResultGenerator.genSuccessResult("设置成功");
        }
        return ResultGenerator.genFailResult("设置失败");
    }
}
