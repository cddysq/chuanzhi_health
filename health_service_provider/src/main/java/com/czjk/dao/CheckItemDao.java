package com.czjk.dao;

import com.czjk.pojo.CheckItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

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

}
