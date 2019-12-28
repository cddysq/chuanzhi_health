package com.czjk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.Result;
import com.czjk.pojo.Setmeal;
import com.czjk.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2019/12/28 10:20
 * @Description: 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    /**
     * 获取所有套餐
     *
     * @return 获取是否成功
     */
    @GetMapping("/getSetmeal")
    public Result getSetmeal() {
        try {
            List<Setmeal> list = setmealService.findAll();
            //查询服务成功
            return Result.builder().flag( true ).message( MessageConstant.GET_SETMEAL_LIST_SUCCESS ).data( list ).build();
        } catch (Exception e) {
            e.printStackTrace();
            //查询服务失败
            return Result.builder().flag( false ).message( MessageConstant.GET_SETMEAL_LIST_FAIL ).build();
        }
    }

    /**
     * 根据套餐ID查询套餐详情
     *
     * @param id 套餐id
     * @return 套餐基本信息、套餐对应的检查组信息、检查组对应的检查项信息
     */
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            Setmeal setmeal = setmealService.findById( id );
            //查詢成功回显
            return Result.builder().flag( true ).message( MessageConstant.QUERY_SETMEAL_SUCCESS ).data( setmeal ).build();
        } catch (Exception e) {
            //查失败功回显
            return Result.builder().flag( false ).message( MessageConstant.QUERY_SETMEAL_FAIL ).build();
        }
    }
}
