package com.czjk.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.dao.MemberDao;
import com.czjk.dao.OrderDao;
import com.czjk.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/3 21:35
 * @Description: 运营数据统计服务
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() {
        //当前时间
        DateTime date = DateUtil.date();
        String today = DateUtil.format( date, "yyyy-MM-dd" );

        //本周起始时间
        String thisWeek = Convert.toStr( DateUtil.beginOfWeek( date ) );

        //本月起始时间
        String thisMonth = Convert.toStr( DateUtil.beginOfMonth( date ) );


        //本日新增会员数
        Integer todayNewMember = memberDao.findMemberCountByDate( today );
        //总会员数
        Integer totalMember = memberDao.findMemberTotalCount();
        //本周新增会员数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate( thisWeek );
        //本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate( thisMonth );
        //今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate( today );
        //本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate( thisWeek );
        //本月预约数
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate( thisMonth );
        //今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate( today );
        //本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate( thisWeek );
        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate( thisMonth );

        //热门套餐查询（取前4）
        List<Map<String, Object>> hotSetmeal = orderDao.findHotSetmeal();

        return MapUtil.<String, Object>builder()
                .put( "reportDate", today )
                .put( "todayNewMember", todayNewMember )
                .put( "totalMember", totalMember )
                .put( "thisWeekNewMember", thisWeekNewMember )
                .put( "thisMonthNewMember", thisMonthNewMember )
                .put( "todayOrderNumber", todayOrderNumber )
                .put( "todayVisitsNumber", todayVisitsNumber )
                .put( "thisWeekOrderNumber", thisWeekOrderNumber )
                .put( "thisWeekVisitsNumber", thisWeekVisitsNumber )
                .put( "thisMonthOrderNumber", thisMonthOrderNumber )
                .put( "thisMonthVisitsNumber", thisMonthVisitsNumber )
                .put( "hotSetmeal", hotSetmeal ).build();
    }
}