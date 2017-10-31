package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.main.requestbody.PushBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@RestController
@RequestMapping("/push")
public class PushController {

    /**
     * 推送指定的id集合到ec
     *
     * @param pushBody
     *         id集合
     * @return 结果
     */
    @PostMapping({"", "/"})
    public BaseData push(@RequestBody PushBody pushBody) {
        return null;
    }
}
