package com.czjk.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.junit.Test;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2019/12/26 15:25
 * @Description: poi读取Excel测试
 */
public class PoiTest {
    @Test
    public void readExcel() {
        ExcelReader reader = ExcelUtil.getReader( "D:\\IdeaProject\\health_parent\\health_backend\\src\\main\\webapp\\template\\ordersetting_template.xlsx" );
        List<List<Object>> lists = reader.read( 1 );
        for (List<Object> list : lists) {
            //System.out.println(list.get(0).toString());
            DateTime dateTime = DateUtil.parse( list.get( 0 ).toString() );
            System.out.println( dateTime );
            //System.out.println(list.get(1).toString());
        }
        //lists.get( 1 ).forEach( System.out::println );
        //System.out.println( lists );
    }
}