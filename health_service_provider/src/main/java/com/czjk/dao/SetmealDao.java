package com.czjk.dao;

import com.czjk.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
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

    /**
     * 分页查询体检套餐
     *
     * @param queryString 分页条件
     * @return 页面结果
     */
    Page<Setmeal> selectByCondition(String queryString);

    /**
     * 查询所有套餐
     *
     * @return 获取是否成功
     */
    List<Setmeal> findAll();

    /**
     * 根据套餐ID查询套餐详情
     *
     * @param id 套餐id
     * @return 套餐基本信息、套餐对应的检查组信息、检查组对应的检查项信息
     */
    Setmeal findById(Integer id);

    /**
     * 根据套餐ID查询套餐
     *
     * @param id 套餐id
     * @return 套餐基本信息
     */
    Setmeal findBySetmeal(Integer id);
}