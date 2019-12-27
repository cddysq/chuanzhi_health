package com.czjk.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.dao.OrderSettingDao;
import com.czjk.pojo.OrderSetting;
import com.czjk.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}