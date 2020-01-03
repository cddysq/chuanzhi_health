package com.czjk.service;

import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2019/12/25 19:57
 * @Description: 体检套餐服务接口
 */
public interface SetmealService {
    /**
     * 新增套餐
     *
     * @param setmeal       套餐信息
     * @param checkGroupIds 检查组id集合
     */
    void add(Setmeal setmeal, Integer[] checkGroupIds);

    /**
     * 分页查询
     *
     * @param queryPageBean 分页条件封装类
     * @return 分页结果
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

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

    /**
     * 查询预约套餐名并统计套餐预约总数
     *
     * @return 套餐名，与之对应的预约总数
     */
    List<Map<String, Object>> findSetmealCount();
}