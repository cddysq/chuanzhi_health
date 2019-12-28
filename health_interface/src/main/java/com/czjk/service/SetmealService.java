package com.czjk.service;

import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.pojo.Setmeal;

import java.util.List;

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
}