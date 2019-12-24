package com.czjk.dao;

import com.czjk.pojo.CheckGroup;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2019/12/24 20:01
 * @Description: 检查组数据接口
 */
public interface CheckGroupDao {
    /**
     * 添加检查组
     *
     * @param checkGroup 检查组信息
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组和检查项多对多关系
     *
     * @param map 检查组id合检查项id
     */
    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    /**
     * 分页查询检查组
     *
     * @param queryString 分页条件
     * @return 页面结果
     */
    Page<CheckGroup> selectByCondition(String queryString);
}
