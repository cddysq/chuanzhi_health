package com.czjk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czjk.constant.MessageConstant;
import com.czjk.dao.AddressDao;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.entity.Result;
import com.czjk.pojo.Address;
import com.czjk.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地址服务
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/3 15:09
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressDao addressDao;

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Page<Address> page = new Page<>();
        page.setSize( queryPageBean.getPageSize() );
        page.setCurrent( queryPageBean.getCurrentPage() );

        IPage<Address> iPage = addressDao.selectPage( page,
                new LambdaQueryWrapper<Address>()
                        .like( Address::getName, queryPageBean.getQueryString() )
        );
        return PageResult.builder()
                //返回总条数
                .total( iPage.getTotal() )
                //返回分页数据集合
                .rows( iPage.getRecords() ).build();
    }

    @Override
    public void deleteById(Integer id) {
        addressDao.deleteById( id );
    }

    @Override
    public List<Address> findAll() {
        return addressDao.selectList( null );
    }

    @Override
    public Result add(Address address) {
        Address templateAddress = addressDao.selectOne(
                new LambdaQueryWrapper<Address>()
                        // 根据名称查询地址
                        .eq( Address::getName, address.getName() )
        );
        if (templateAddress != null) {
            return Result.builder().flag( false ).message( MessageConstant.ADD_ADDRESS_ERROR ).build();
        } else {
            addressDao.insert( address );
            return Result.builder().flag( true ).message( MessageConstant.ADD_ADDRESS_SUCCESS ).build();
        }
    }

}