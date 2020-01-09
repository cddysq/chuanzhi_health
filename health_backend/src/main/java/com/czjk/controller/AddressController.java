package com.czjk.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.entity.Result;
import com.czjk.pojo.Address;
import com.czjk.service.AddressService;
import com.czjk.utils.MapUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 分页查询
     *
     * @param queryPageBean 分页条件
     * @return 分页结果数据封装对象
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return addressService.pageQuery( queryPageBean );
    }

    /**
     * 删除公司地址
     *
     * @param id 地址id
     * @return 成功或失败对应提示
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        try {
            addressService.deleteById( id );
        } catch (Exception e) {
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.DELETE_ADDRESS_FAIL ).build();
        }
        //服务调用成功
        return Result.builder().flag( true ).message( MessageConstant.DELETE_ADDRESS_SUCCESS ).build();
    }

    /**
     * 查询所有公司地址
     *
     * @return 成功或失败对应提示
     */
    @GetMapping("/findAll")
    public Result findAll() {
        try {
            List<Address> addressList = addressService.findAll();
            //服务调用成功
            return Result.builder().flag( true ).message( MessageConstant.GET_ADDRESS_SUCCESS ).data( addressList ).build();
        } catch (Exception e) {
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.GET_ADDRESS_FAIL ).build();
        }
    }

    /**
     * 新增公司地址
     *
     * @return 成功或失败对应提示
     */
    @PostMapping("/add")
    public Result add(@RequestBody String addressName) {
        //解码url去掉后缀得到真实地址名
        String name = StrUtil.removeSuffix( URLUtil.decode( addressName ), "=" );
        //通过工具类拿到经纬度
        String[] lngAndLat = StrUtil.split( MapUtils.getLngAndLat( name ), "," );
        Address address = Address.builder().name( name )
                .longitude( Convert.toBigDecimal( lngAndLat[0] ) )
                .latitude( Convert.toBigDecimal( lngAndLat[1] ) ).build();
        try {
            //服务调用成功
            return addressService.add( address );
        } catch (Exception e) {
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.ADD_ADDRESS_FAIL ).build();
        }
    }
}