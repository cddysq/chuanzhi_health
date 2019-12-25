package com.czjk.service;

import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.pojo.CheckGroup;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2019/12/24 19:37
 * @Description: 检查组服务接口
 */
public interface CheckGroupService {

    /**
     * 新增检查组
     *
     * @param checkGroup   检查组信息
     * @param checkitemIds 与检查组关联的检查项id
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 分页查询
     *
     * @param queryPageBean 分页条件封装类
     * @return 分页结果
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 查询检查组
     *
     * @param id 检查组id
     * @return 指定检查组信息
     */
    CheckGroup findById(Integer id);

    /**
     * 根据检查组id查询对应的所有检查项id
     *
     * @param id 检查组id
     * @return 指定检查组关联的所有检查项
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    /**
     * 编辑检查组
     *
     * @param checkGroup   检查组信息
     * @param checkItemIds 与检查组关联的检查项id
     */
    void edit(CheckGroup checkGroup, Integer[] checkItemIds);

    /**
     * 删除检查组
     *
     * @param id 检查组id
     */
    void deleteById(Integer id);

    /**
     * 查询所欲检查组
     *
     * @return 检查组信息集合
     */
    List<CheckGroup> findAll();
}