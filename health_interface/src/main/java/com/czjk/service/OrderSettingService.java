package com.czjk.service;

import com.czjk.pojo.OrderSetting;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2019/12/26 21:25
 * @Description: 预约服务接口
 */
public interface OrderSettingService {
    /**
     * 批量导入预约设置数据
     *
     * @param data 预约设置信息
     */
    void add(List<OrderSetting> data);
}