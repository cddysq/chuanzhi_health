package com.czjk.dao;

import com.czjk.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2019/12/22 18:57
 * @Description: 检查项数据接口
 */
public interface CheckItemDao {
    /**
     * 添加检查项
     *
     * @param checkitem 检查项
     */
    void add(CheckItem checkitem);

    /**
     * 分页查询检查项
     *
     * @param queryString 分页条件
     * @return 页面结果
     */
    Page<CheckItem> selectByCondition(String queryString);

    /**
     * 查找检查项是否和检查组有关联
     *
     * @param checkItemId 检查项id
     * @return 检查项与检查组关联表中当前检查项关联总数
     */
    Long findCountByCheckItemId(Integer checkItemId);

    /**
     * 删除检查项
     *
     * @param id 检查项id
     */
    void deleteById(Integer id);

    /**
     * 更新检查项信息
     *
     * @param checkitem 检查项数据
     */
    void edit(CheckItem checkitem);

    /**
     * 根据id查询检查项
     *
     * @param id 检查项id
     * @return 对应检查项数据
     */
    CheckItem findById(Integer id);

    /**
     * 查询所有检查项
     *
     * @return 检查项信息集合
     */
    List<CheckItem> findAll();
}