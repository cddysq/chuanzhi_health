package com.czjk.dao;

import com.czjk.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2019/12/27 18:06
 * @Description: 预约数据接口
 */
public interface OrderSettingDao {
    /**
     * 根据日期查询是否存在预约
     *
     * @param orderDate 当前的日期
     * @return 本日是否进行了预约设置
     */
    Long findCountByOrderDate(Date orderDate);

    /**
     * 更新当前日期的预约数据
     *
     * @param orderSetting 预约数据
     */
    void updateNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 添加预约数据
     *
     * @param orderSetting 预约数据
     */
    void add(OrderSetting orderSetting);

    /**
     * 根据月份查询预约设置信息
     *
     * @param map 当前月起始日期和结束日期
     * @return 当前月所有预约数据
     */
    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);
}