package com.czjk.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.constant.MessageConstant;
import com.czjk.dao.AddressDao;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.entity.Result;
import com.czjk.pojo.Address;
import com.czjk.service.AddressService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/1/7 9:06
 * @Description: 地址服务
 */
@Service(interfaceClass = AddressService.class)
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        //使用 PageHelper 完成分页查询
        Page<Address> page = PageHelper.startPage(
                queryPageBean.getCurrentPage(), queryPageBean.getPageSize() ).
                doSelectPage( () -> addressDao.selectByCondition( queryPageBean.getQueryString() ) );
        return PageResult.builder()
                //返回总条数
                .total( page.getTotal() )
                //返回分页数据集合
                .rows( page.getResult() ).build();
    }

    @Override
    public void deleteById(Integer id) {
        addressDao.deleteById( id );
    }

    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    @Override
    public Result add(Address address) {
        Address templateAddress = addressDao.findByName( address.getName() );
        if (templateAddress != null) {
            return Result.builder().flag( false ).message( MessageConstant.ADD_ADDRESS_ERROR ).build();
        } else {
            addressDao.add( address );
            return Result.builder().flag( true ).message( MessageConstant.ADD_ADDRESS_SUCCESS ).build();
        }
    }
}