package com.czjk.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.dao.CheckGroupDao;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.pojo.CheckGroup;
import com.czjk.pojo.CheckItem;
import com.czjk.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2019/12/24 19:39
 * @Description: 检查组服务
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //新增检查组，操作t_checkgroup表
        checkGroupDao.add( checkGroup );
        //设置检查组和检查项的多对多的关联关系，操作t_checkgroup_checkitem表
        this.setCheckGroupAndCheckItem( checkGroup.getId(), checkitemIds );
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        //使用 PageHelper 完成分页查询
        Page<CheckItem> page = PageHelper.startPage(
                queryPageBean.getCurrentPage(), queryPageBean.getPageSize() ).
                doSelectPage( () -> checkGroupDao.selectByCondition( queryPageBean.getQueryString() ) );
        return PageResult.builder()
                //返回总条数
                .total( page.getTotal() )
                //返回分页数据集合
                .rows( page.getResult() ).build();
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById( id );
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId( id );
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        //修改检查组基本信息，操作检查组t_checkgroup表
        checkGroupDao.edit( checkGroup );
        //根据检查组id删除中间表数据（清理原有关联关系）
        checkGroupDao.deleteAssociation( checkGroup.getId() );
        //建立检查组和检查项的最新关联关系
        this.setCheckGroupAndCheckItem( checkGroup.getId(), checkItemIds );
    }

    @Override
    public void deleteById(Integer id) {
        //根据检查组id清理中间表关联关系）
        checkGroupDao.deleteAssociation( id );
        //删除检查组信息
        checkGroupDao.deleteById( id );
    }

    /**
     * 设置检查组合和检查项的关联关系
     *
     * @param checkGroupId 检查组id
     * @param checkitemIds 检查项id
     */
    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if (ArrayUtil.isNotEmpty( checkitemIds )) {
            Map<String, Integer> map = new HashMap<>( 0 );
            for (Integer checkitemId : checkitemIds) {
                map.put( "checkgroupId", checkGroupId );
                map.put( "checkitemId", checkitemId );
                checkGroupDao.setCheckGroupAndCheckItem( map );
            }
        }
    }
}
