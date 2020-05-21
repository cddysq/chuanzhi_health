package com.czjk.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.Result;
import com.czjk.pojo.OrderSetting;
import com.czjk.service.OrderSettingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 预约设置
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/21 15:01
 **/
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * Excel文件上传，并解析文件内容保存到数据库
     *
     * @param excelFile 前端传递过来的excel文件
     * @return 上传是否成功
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            //读取excel数据
            ExcelReader reader = ExcelUtil.getReader( excelFile.getInputStream() );
            //跳过第一行
            List<List<Object>> lists = reader.read( 1 );
            List<OrderSetting> data = new ArrayList<>();
            for (List<Object> list : lists) {
                DateTime dateTime = DateUtil.parse( list.get( 0 ).toString() );
                int number = Integer.parseInt( list.get( 1 ).toString() );
                //循环向对象添加数据
                OrderSetting orderSetting = OrderSetting.builder().orderDate( dateTime ).number( number ).build();
                data.add( orderSetting );
            }
            //通过dubbo远程调用服务实现数据批量导入到数据库
            orderSettingService.add( data );
            //导入成功回显
            return Result.builder().flag( true ).message( MessageConstant.IMPORT_ORDERSETTING_SUCCESS ).build();
        } catch (IOException e) {
            e.printStackTrace();
            //导入失败回显
            return Result.builder().flag( false ).message( MessageConstant.IMPORT_ORDERSETTING_FAIL ).build();
        }
    }

    /**
     * 根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
     *
     * @param date 当前日期 date格式为：yyyy-MM
     * @return 当前月预约数据
     */
    @GetMapping("getOrderSettingByMonth/{date}")
    public Result getOrderSettingByMonth(@PathVariable("date") String date) {
        try {
            List<Map<String, Object>> list = orderSettingService.getOrderSettingByMonth( date );
            //查询预约成功回显
            return Result.builder().flag( true ).message( MessageConstant.GET_ORDERSETTING_SUCCESS ).data( list ).build();
        } catch (Exception e) {
            e.printStackTrace();
            //查询预约失败回显
            return Result.builder().flag( false ).message( MessageConstant.GET_ORDERSETTING_FAIL ).build();
        }
    }

    /**
     * 根根据指定日期修改可预约人数
     *
     * @param orderSetting 预约数据
     * @return 是否修改成功
     */
    @PutMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.updateNumberByDate( orderSetting );
            //修改成功回显
            return Result.builder().flag( true ).message( MessageConstant.ORDERSETTING_SUCCESS ).build();
        } catch (Exception e) {
            e.printStackTrace();
            //修改失败回显
            return Result.builder().flag( false ).message( MessageConstant.ORDERSETTING_FAIL ).build();
        }
    }
}