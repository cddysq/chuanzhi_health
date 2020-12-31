package com.czjk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.entity.Result;
import com.czjk.pojo.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

/**
 * mp 分页测试
 *
 * @author Haotian
 * @version 2.0
 * @date 2020/12/31 10:30
 */
@SpringBootTest
public class AddressServiceTest {
    @Autowired
    private AddressService addressService;

    @Test
    public void pageQueryTest() {
        QueryPageBean queryPageBean = QueryPageBean.builder().pageSize( 6 ).currentPage( 1 ).queryString( "成都" ).build();
        PageResult pageResult = addressService.pageQuery( queryPageBean );
        System.out.println( JSON.toJSONString( pageResult, SerializerFeature.PrettyFormat ) );
    }

    @Test
    public void findAll() {
        List<Address> addressList = addressService.findAll();
        System.out.println( JSON.toJSONString( addressList, SerializerFeature.PrettyFormat ) );
    }

    @Test
    public void deleteById() {
        addressService.deleteById( 4 );
    }

    @Test
    public void add() {
        Address address = Address.builder()
                .name( "日本东京都多摩郡瑞穗町西大字駒形富士山" )
                .longitude( new BigDecimal( "139.1899109" ) )
                .latitude( new BigDecimal( "36.31150062" ) )
                .build();
        Result result = addressService.add( address );
        System.out.println( JSON.toJSONString( result, SerializerFeature.PrettyFormat ) );
    }

}
