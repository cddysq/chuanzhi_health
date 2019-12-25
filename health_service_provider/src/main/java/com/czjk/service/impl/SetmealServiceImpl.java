package com.czjk.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.dao.SetmealDao;
import com.czjk.pojo.Setmeal;
import com.czjk.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2019/12/25 20:02
 * @Description: 体检套餐服务实现类
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealDao.add( setmeal );
        //绑定套餐和检查组的多对多关系
        this.setSetmealAndCheckGroup( setmeal.getId(), checkGroupIds );
    }

    /**
     * 绑定套餐和检查组的多对多关系
     *
     * @param setmealId     套餐id
     * @param checkGroupIds 检查组id
     */
    public void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkGroupIds) {
        if (ArrayUtil.isNotEmpty( checkGroupIds )) {
            Map<String, Integer> map = new HashMap<>( 0 );
            for (Integer checkGroupId : checkGroupIds) {
                map.put( "setmealId", setmealId );
                map.put( "checkgroupId", checkGroupId );
                setmealDao.setSetmealAndCheckGroup( map );
            }
        }
    }
}