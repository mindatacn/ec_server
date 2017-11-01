package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.model.secondary.PtAnalyContactCount;
import com.mindata.ecserver.main.service.AnalyContactCountService;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@RestController
@RequestMapping("/analyCount")
public class AnalyContactCountController {
    /**
     * service
     */
    @Resource
    private AnalyContactCountService analyContactCountService;

    /**
     * 查询某段时间统计信息
     *
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 信息集合
     */
    @GetMapping({"", "/"})
    public BaseData count(String begin, String end) {
        if (DateUtil.parseDate(begin).after(DateUtil.parseDate(end))) {
            return ResultGenerator.genFailResult("开始时间大于结束时间");
        }
        List<PtAnalyContactCount> entities = analyContactCountService.findByDateBetween(begin, end);
        return ResultGenerator.genSuccessResult(entities);
    }
}
