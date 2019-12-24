package com.czjk.service;

import com.czjk.pojo.CheckGroup;

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
}
