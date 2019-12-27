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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2019/12/26 20:54
 * @Description: 预约设置
 */
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
}