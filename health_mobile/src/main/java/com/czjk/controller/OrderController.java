package com.czjk.controller;

import cn.hutool.core.convert.Convert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.constant.RedisMessageConstant;
import com.czjk.entity.Result;
import com.czjk.pojo.Order;
import com.czjk.service.OrderService;
import com.czjk.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2019/12/30 20:44
 * @Description: 体检预约处理
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    ;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 用户预约
     *
     * @param map 表单数据
     * @return 预约失败提示 or 预约成功跳转成功界面
     */
    @PostMapping("/submit")
    public Result submitOrder(@RequestBody Map<String, Object> map) {
        String telephone = Convert.toStr( map.get( "telephone" ) );
        String validateCode = Convert.toStr( map.get( "validateCode" ) );
        //从Redis中获取缓存的验证码，key为手机号+RedisConstant.SENDTYPE_ORDER
        String validateCodeInRedis = jedisPool.getResource().get( telephone + RedisMessageConstant.SENDTYPE_ORDER );
        //校验手机验证码
        if (validateCodeInRedis == null || !validateCodeInRedis.equals( validateCode )) {
            return Result.builder().flag( false ).message( MessageConstant.VALIDATECODE_ERROR ).build();
        }
        //比对成功，调用服务完成预约业务处理
        //设置预约类型，分为微信预约、电话预约
        map.put( "orderType", Order.ORDERTYPE_WEIXIN );
        Result result = Result.builder().flag( false ).message( "系统异常，请稍您稍后重试！" ).build();
        try {
            //通过Dubbo远程调用服务实现在线预约业务处理
            result = orderService.order( map );
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常调用失败
            return result;
        }
        if (result.isFlag()) {
            //预约成功，发送短信通知
            try {
                SMSUtils.sendShortMessage( SMSUtils.ORDER_NOTICE, telephone, "666666" );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据预约订单id查询预约信息
     *
     * @param id 预约订单id
     * @return 套餐信息和会员信息
     */
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            Map<String, Object> map = orderService.findById( id );
            //查询成功
            return Result.builder().flag( true ).message( MessageConstant.QUERY_ORDER_SUCCESS ).data( map ).build();
        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return Result.builder().flag( false ).message( MessageConstant.QUERY_ORDER_FAIL ).build();
        }
    }
}