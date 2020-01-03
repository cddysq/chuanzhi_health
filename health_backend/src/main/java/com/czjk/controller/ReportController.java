package com.czjk.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.Result;
import com.czjk.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/3 18:23
 * @Description: 报表操作
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;

    /**
     * 会员数量统计
     *
     * @return 过去一年每月会员总数
     */
    @GetMapping("/getMemberReport")
    public Result getMemberReport() {
        //封装前端ECharts对应数据
        Map<String, Object> map = new HashMap<>( 0 );
        //储存所有月份
        List<String> months = new ArrayList<>();
        //当前时间前移12月，得到统计历史开始时间
        DateTime startTime = DateUtil.offsetMonth( DateUtil.date(), -12 );
        //历史时间依次后推一个月
        for (int i = 0; i < 12; i++) {
            startTime = DateUtil.offsetMonth( startTime, 1 );
            String month = DateUtil.format( startTime, "yyyy.MM" );
            months.add( month );
        }
        map.put( "months", months );
        try {
            List<Integer> memberCount = memberService.findMemberCountByMonths( months );
            map.put( "memberCount", memberCount );
            return Result.builder().flag( true ).message( MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS ).data( map ).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.builder().flag( false ).message( MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL ).build();
        }
    }
}
