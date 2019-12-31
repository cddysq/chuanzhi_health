package com.czjk.dao;

import com.czjk.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2019/12/30 21:47
 * @Description: 订单接口
 */
public interface OrderDao {
    /**
     * 添加体检预约信息
     *
     * @param order 体检预约数据
     */
    void add(Order order);

    /**
     * 根据体检预约信息查询订单
     *
     * @param order 体检预约信息
     * @return 预约信息
     */
    List<Order> findByCondition(Order order);

    Map findById4Detail(Integer id);

    Integer findOrderCountByDate(String date);

    Integer findOrderCountAfterDate(String date);

    Integer findVisitsCountByDate(String date);

    Integer findVisitsCountAfterDate(String date);

    List<Map> findHotSetmeal();
}