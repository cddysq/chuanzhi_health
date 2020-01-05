package com.czjk.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.Result;
import com.czjk.service.MemberService;
import com.czjk.service.ReportService;
import com.czjk.service.SetmealService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
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
    @Reference
    private ReportService reportService;

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

    /**
     * 运营数据统计
     *
     * @return Map数据格式：
     * reportDate -> 当前时间
     * todayNewMember -> 本日新增会员数
     * totalMember -> 总会员数
     * thisWeekNewMember -> 本周新增会员数
     * thisMonthNewMember -> 本月新增会员数
     * todayOrderNumber -> 今日预约数
     * todayVisitsNumber -> 今日到诊数
     * thisWeekOrderNumber -> 本周预约数
     * thisWeekVisitsNumber -> 本周到诊数
     * thisMonthOrderNumber -> 本月预约数
     * thisMonthVisitsNumber -> 本月到诊数
     * hotSetmeal -> List<Setmeal> 热门套餐
     */
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> data = reportService.getBusinessReportData();
            return Result.builder().flag( true ).message( MessageConstant.GET_BUSINESS_REPORT_SUCCESS ).data( data ).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.builder().flag( false ).message( MessageConstant.GET_BUSINESS_REPORT_FAIL ).build();
        }
    }

    /**
     * 导出运营数据Excel报表
     *
     * @return 是否导出成功
     */
    @GetMapping("/exportBusinessReport4Excel")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        //创建页面输出流
        ServletOutputStream outputStream = null;
        //创建excel输出流
        ExcelWriter writer = null;
        try {
            outputStream = response.getOutputStream();
            //远程调用报表服务获取报表数据
            Map<String, Object> data = reportService.getBusinessReportData();
            //获得Excel模板文件绝对路径
            String excelPath = request.getSession().getServletContext().getRealPath( "template" ) + File.separator + "report_template.xlsx";

            //读取excel设置数据
            ExcelReader excel = ExcelUtil.getReader( excelPath );
            ExcelReader setSheet = excel.setSheet( 0 );
            setSheet.getCell( 5, 2 ).setCellValue( Convert.toStr( data.get( "reportDate" ) ) );
            setSheet.getCell( 5, 4 ).setCellValue( Convert.toInt( data.get( "todayNewMember" ) ) );
            setSheet.getCell( 7, 4 ).setCellValue( Convert.toInt( data.get( "totalMember" ) ) );
            setSheet.getCell( 5, 5 ).setCellValue( Convert.toInt( data.get( "thisWeekNewMember" ) ) );
            setSheet.getCell( 7, 5 ).setCellValue( Convert.toInt( data.get( "thisMonthNewMember" ) ) );
            setSheet.getCell( 5, 7 ).setCellValue( Convert.toInt( data.get( "todayOrderNumber" ) ) );
            setSheet.getCell( 7, 7 ).setCellValue( Convert.toInt( data.get( "todayVisitsNumber" ) ) );
            setSheet.getCell( 5, 8 ).setCellValue( Convert.toInt( data.get( "thisWeekOrderNumber" ) ) );
            setSheet.getCell( 7, 8 ).setCellValue( Convert.toInt( data.get( "thisWeekVisitsNumber" ) ) );
            setSheet.getCell( 5, 9 ).setCellValue( Convert.toInt( data.get( "thisMonthOrderNumber" ) ) );
            setSheet.getCell( 7, 9 ).setCellValue( Convert.toInt( data.get( "thisMonthVisitsNumber" ) ) );
            List<Map> hotSetmeal = Convert.toList( Map.class, data.get( "hotSetmeal" ) );
            int row = 12;
            for (Map map : hotSetmeal) {
                setSheet.getCell( 4, row ).setCellValue( Convert.toStr( map.get( "name" ) ) );
                setSheet.getCell( 5, row ).setCellValue( Convert.toLong( map.get( "setmeal_count" ) ) );
                setSheet.getCell( 6, row ).setCellValue( Convert.toBigDecimal( map.get( "proportion" ) ).doubleValue() );
                row++;
            }

            //写出excel
            writer = excel.getWriter();
            //代表的是Excel文件类型
            response.setContentType( "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8" );
            //指定以附件形式进行下载
            response.setHeader( "Content-Disposition", "attachment;filename=report.xlsx" );
            writer.flush( outputStream, true );
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.builder().flag( false ).message( MessageConstant.GET_BUSINESS_REPORT_FAIL ).build();
        } finally {
            // 关闭writer，释放内存
            Objects.requireNonNull( writer ).close();
            IoUtil.close( outputStream );
        }
    }

    /**
     * 导出运营数据PDF报表
     *
     * @return 是否导出成功
     */
    @GetMapping("/exportBusinessReport4PDF")
    public Result exportBusinessReport4PDF(HttpServletRequest request, HttpServletResponse response) {
        try {
            //远程调用报表服务获取报表数据
            Map<String, Object> data = reportService.getBusinessReportData();
            List<Map> hotSetmeal = Convert.toList( Map.class, data.get( "hotSetmeal" ) );

            //获得pdf模板文件绝对路径
            String jrxmlPath = request.getSession().getServletContext().getRealPath( "template" ) + File.separator + "health_business.jrxml";
            String jasperPath = request.getSession().getServletContext().getRealPath( "template" ) + File.separator + "health_business.jasper";
            //编译模板
            JasperCompileManager.compileReportToFile( jrxmlPath, jasperPath );
            //填充数据---使用JavaBean数据源方式填充
            JasperPrint jasperPrint = JasperFillManager.fillReport( jasperPath, data, new JRBeanCollectionDataSource( hotSetmeal ) );

            ServletOutputStream outputStream = response.getOutputStream();
            //代表的是pdf文件类型
            response.setContentType( "application/pdf;charset=utf-8" );
            //指定以附件形式进行下载
            response.setHeader( "Content-Disposition", "attachment;filename=report.pdf" );
            //输出文件
            JasperExportManager.exportReportToPdfStream( jasperPrint, outputStream );

            outputStream.flush();
            outputStream.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.builder().flag( false ).message( MessageConstant.GET_BUSINESS_REPORT_FAIL ).build();
        }
    }
}