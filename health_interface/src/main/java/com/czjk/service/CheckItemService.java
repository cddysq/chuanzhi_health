package com.czjk.service;

import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.pojo.CheckItem;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2019/12/22 18:41
 * @Description: 检查项服务接口
 */
public interface CheckItemService {

    /**
     * 添加检查项
     *
     * @param checkitem 检查项
     */
    void add(CheckItem checkitem);

    /**
     * 分页查询
     *
     * @param queryPageBean 分页条件封装类
     * @return 分页结果
     */
    PageResult pageQuery(QueryPageBean queryPageBean);


    /**
     * 删除检查项
     *
     * @param id 检查项id
     */
    void deleteById(Integer id);

    /**
     * 编辑检查项信息
     *
     * @param checkitem 检查项数据
     */
    void edit(CheckItem checkitem);

    /**
     * 查询检查项信息
     *
     * @param id 检查项id
     * @return 指定检查项数据
     */
    CheckItem findById(Integer id);

    /**
     * 查询所有检查项
     *
     * @return 检查项信息集合
     */
    List<CheckItem> findAll();
}