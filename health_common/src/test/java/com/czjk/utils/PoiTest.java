package com.czjk.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * poi读取Excel测试
 *
 * @author Haotian
 * @version 2.0
 * @date 2020/12/29 10:14
 **/
@Log
public class PoiTest {
    @Test
    public void readExcel() {
        ExcelReader reader = ExcelUtil.getReader( "D:\\IdeaProject\\health_parent\\health_backend\\src\\main\\webapp\\template\\ordersetting_template.xlsx" );
        List<List<Object>> lists = reader.read( 1 );
        for (List<Object> list : lists) {
            DateTime dateTime = DateUtil.parse( list.get( 0 ).toString() );
            System.out.println( dateTime );
        }
    }
}