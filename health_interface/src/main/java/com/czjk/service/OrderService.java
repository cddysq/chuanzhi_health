package com.czjk.service;

import com.czjk.entity.Result;

import java.util.Map;

/**
 * 体检预约服务接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/28 16:30
 **/
public interface OrderService {
    /**
     * 体检预约
     *
     * @param map 预约数据
     * @return 是否预约成功
     */
    Result order(Map<String, Object> map);

    /**
     * 根据id查询预约信息
     *
     * @param id 预约订单id
     * @return 体检人信息、套餐信息
     */
    Map<String, Object> findById(Integer id);
}