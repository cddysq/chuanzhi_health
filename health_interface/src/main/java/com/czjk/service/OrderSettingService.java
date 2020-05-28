package com.czjk.service;

import com.czjk.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 预约服务接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/28 16:30
 **/
public interface OrderSettingService {
    /**
     * 批量导入预约设置数据
     *
     * @param data 预约设置信息
     */
    void add(List<OrderSetting> data);

    /**
     * 查询当前月的预约信息
     *
     * @param date 当前日期 参数格式为：yyyy-MM
     * @return 当前月预约信息
     */
    List<Map<String, Object>> getOrderSettingByMonth(String date);

    /**
     * 根根据指定日期修改可预约人数
     *
     * @param orderSetting 预约数据
     */
    void updateNumberByDate(OrderSetting orderSetting);
}