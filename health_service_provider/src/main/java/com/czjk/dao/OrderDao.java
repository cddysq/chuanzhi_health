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
    public void add(Order order);

    public List<Order> findByCondition(Order order);

    public Map findById4Detail(Integer id);

    public Integer findOrderCountByDate(String date);

    public Integer findOrderCountAfterDate(String date);

    public Integer findVisitsCountByDate(String date);

    public Integer findVisitsCountAfterDate(String date);

    public List<Map> findHotSetmeal();
}