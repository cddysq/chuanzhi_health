package com.czjk.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.dao.CheckItemDao;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.pojo.CheckItem;
import com.czjk.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查项服务
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/18 14:21
 **/
@Service(interfaceClass = CheckItemService.class)
@Transactional(rollbackFor = Exception.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkitem) {
        checkItemDao.add( checkitem );
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        //使用 PageHelper 完成分页查询
        Page<CheckItem> page = PageHelper.startPage(
                queryPageBean.getCurrentPage(), queryPageBean.getPageSize() ).
                doSelectPage( () -> checkItemDao.selectByCondition( queryPageBean.getQueryString() ) );
        return PageResult.builder()
                //返回总条数
                .total( page.getTotal() )
                //返回分页数据集合
                .rows( page.getResult() ).build();
    }

    @Override
    public void deleteById(Integer id) {
        //查询当前检查项是否和检查组关联
        Long count = checkItemDao.findCountByCheckItemId( id );
        if (count > 0) {
            //当前检查项被引用，不能删除
            throw new RuntimeException( "当前检查项被引用，不能删除" );
        }
        //不存在正常删除
        checkItemDao.deleteById( id );
    }

    @Override
    public void edit(CheckItem checkitem) {
        checkItemDao.edit( checkitem );
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById( id );
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}