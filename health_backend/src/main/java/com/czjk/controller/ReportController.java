package com.czjk.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.Result;
import com.czjk.service.MemberService;
import com.czjk.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Reference
    private SetmealService setmealService;

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

    /**
     * 套餐预约占比统计
     *
     * @return 套餐对应预约数量
     */
    @GetMapping("/getSetmealReport")
    public Result getSetmealReport() {
        //封装前端ECharts对应数据
        Map<String, Object> data = new HashMap<>( 0 );
        try {
            List<Map<String, Object>> setmealConut = setmealService.findSetmealCount();
            data.put( "setmealConut", setmealConut );
            data.put( "setmealNames",
                    setmealConut.stream()
                            //获取到每一个套餐的名称
                            .map( m -> Convert.toStr( m.get( "name" ) ) )
                            //收集为名字集合
                            .collect( Collectors.toList() ) );
            //请求成功，返回页面所需数据
            return Result.builder().flag( true ).message( MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS ).data( data ).build();
        } catch (Exception e) {
            e.printStackTrace();
            //请求失败，回显错误提示
            return Result.builder().flag( false ).message( MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL ).build();
        }
    }
}