package com.czjk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.Result;
import com.czjk.pojo.Address;
import com.czjk.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/7 9:02
 * @Description: 地址管理
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Reference
    private AddressService addressService;

    /**
     * 查询所有公司地址
     *
     * @return 成功或失败对应提示
     */
    @GetMapping("/findAll")
    public Result findAll() {
        try {
            List<Address> addressList = addressService.findAll();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = null;
            for (Address address : addressList) {
                map = new HashMap<>( 0 );
                map.put( "id", address.getId() );
                map.put( "value", address.getName() );
                list.add( map );
            }
            //服务调用成功
            return Result.builder().flag( true ).message( MessageConstant.GET_ADDRESS_SUCCESS ).data( list ).build();
        } catch (Exception e) {
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.GET_ADDRESS_FAIL ).build();
        }
    }
}