package com.czjk.service;

import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.entity.Result;
import com.czjk.pojo.Address;

import java.util.List;

/**
 * 地址管理服务
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/28 16:29
 **/
public interface AddressService {

    /**
     * 分页查询
     *
     * @param queryPageBean 分页条件封装类
     * @return 分页结果
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 删除公司地址
     *
     * @param id 地址id
     */
    void deleteById(Integer id);

    /**
     * 查询所有公司地址
     *
     * @return 公司地址集合
     */
    List<Address> findAll();

    /**
     * 添加公司地址
     *
     * @param address 公司地址名经纬度
     * @return 地址添加提示
     */
    Result add(Address address);
}