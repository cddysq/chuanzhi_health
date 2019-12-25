package com.czjk.dao;

import com.czjk.pojo.Setmeal;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2019/12/25 20:04
 * @Description: 套餐数据接口
 */
public interface SetmealDao {
    /**
     * 添加套餐
     *
     * @param setmeal 套餐信息
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐和检查组多对多关系
     *
     * @param map 套餐id合检查组id
     */
    void setSetmealAndCheckGroup(Map<String, Integer> map);
}
