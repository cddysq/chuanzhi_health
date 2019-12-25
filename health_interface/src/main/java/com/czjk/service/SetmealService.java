package com.czjk.service;

import com.czjk.pojo.Setmeal;

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
}
