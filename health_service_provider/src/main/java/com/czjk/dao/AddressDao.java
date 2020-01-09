package com.czjk.dao;

import com.czjk.pojo.Address;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/1/7 9:00
 * @Description: 地址数据接口
 */
public interface AddressDao {

    /**
     * 分页查询检地址
     *
     * @param queryString 分页条件
     * @return 页面结果
     */
    Page<Address> selectByCondition(String queryString);

    /**
     * 根据公司地址id删除公司地址
     *
     * @param id 公司地址id
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
     */
    void add(Address address);

    /**
     * 更据地址名称称查询公司
     *
     * @param addressName 公司名
     * @return 公司信息
     */
    Address findByName(String addressName);
}