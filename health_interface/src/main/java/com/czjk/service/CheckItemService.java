package com.czjk.service;

import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.pojo.CheckItem;

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


}