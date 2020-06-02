package com.czjk.dao;

import com.czjk.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * 订单接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/2 17:27
 **/
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

    /**
     * 根据预约id查询预约信息，
     *
     * @param id 预约订单id
     * @return 包括体检人信息、套餐信息
     */
    Map<String, Object> findById4Detail(Integer id);

    /**
     * 根据指定时间统计预约数
     *
     * @param date 指定时间
     * @return 当前预约总数
     */
    Integer findOrderCountByDate(String date);

    /**
     * 统计指定时间之后的预约数
     *
     * @param date 指定时间
     * @return 预约总数
     */
    Integer findOrderCountAfterDate(String date);

    /**
     * 统计指定时间到诊数
     *
     * @param date 指定时间
     * @return 到诊总数
     */
    Integer findVisitsCountByDate(String date);

    /**
     * 统计指定时间之后的到诊数
     *
     * @param date 指定时间
     * @return 到诊总数
     */
    Integer findVisitsCountAfterDate(String date);

    /**
     * 查询热门套餐
     *
     * @return 套餐预约数量前四名
     */
    List<Map<String, Object>> findHotSetmeal();
}