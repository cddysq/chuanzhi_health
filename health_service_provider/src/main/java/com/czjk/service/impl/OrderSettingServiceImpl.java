package com.czjk.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.dao.OrderSettingDao;
import com.czjk.pojo.OrderSetting;
import com.czjk.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2019/12/27 18:03
 * @Description: 预约设置服务
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> data) {
        //先判断是否有预约数据存在
        if (CollUtil.isNotEmpty( data )) {
            for (OrderSetting orderSetting : data) {
                //判断当前日期是否已经进行了预约设置
                Long countByOrderDate = orderSettingDao.findCountByOrderDate( orderSetting.getOrderDate() );
                if (countByOrderDate > 0) {
                    //已经存在，执行更新操作
                    orderSettingDao.updateNumberByOrderDate( orderSetting );
                } else {
                    //不存在，执行添加操作
                    orderSettingDao.add( orderSetting );
                }
            }
        }
    }

    @Override
    public List<Map<String, Object>> getOrderSettingByMonth(String date) {
        //得到当前月份第一天
        String dateBegin = date + "-1";
        //得到当前月份最后一天
        String dateEnd = date + "-31";
        Map<String, String> map = new HashMap<>();
        map.put( "dateBegin", dateBegin );
        map.put( "dateEnd", dateEnd );
        //根据日期范围查询预约设置数据
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth( map );
        //储存当前月预约数据集合
        List<Map<String, Object>> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map<String, Object> m = new HashMap<>( 0 );
            //获取当前是几号
            m.put( "date", orderSetting.getOrderDate().getDate() );
            //获取当前可预约人数
            m.put( "number", orderSetting.getNumber() );
            //获取当前已预约人数
            m.put( "reservations", orderSetting.getReservations() );
            data.add( m );
        }
        return data;
    }

    @Override
    public void updateNumberByDate(OrderSetting orderSetting) {
        //判断当前日期是否已经进行了预约设置
        Long countByOrderDate = orderSettingDao.findCountByOrderDate( orderSetting.getOrderDate() );
        if (countByOrderDate > 0) {
            //已经存在，执行更新操作
            orderSettingDao.updateNumberByOrderDate( orderSetting );
        } else {
            //不存在，执行添加操作
            orderSettingDao.add( orderSetting );
        }
    }
}