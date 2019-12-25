package com.czjk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.entity.Result;
import com.czjk.pojo.CheckGroup;
import com.czjk.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2019/12/24 19:26
 * @Description: 体检检查组管理
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 新增检查组
     *
     * @param checkGroup   检查组信息
     * @param checkItemIds 与检查组关联的检查项id
     * @return 新增成功或者失败提示
     */
    @PostMapping("/add/{checkItemIds}")
    public Result add(@RequestBody CheckGroup checkGroup, @PathVariable("checkItemIds") Integer[] checkItemIds) {
        try {
            checkGroupService.add( checkGroup, checkItemIds );
        } catch (Exception e) {
            e.printStackTrace();
            //新增检查组失败
            return Result.builder().flag( false ).message( MessageConstant.ADD_CHECKGROUP_FAIL ).build();
        }
        //新增检查组成功
        return Result.builder().flag( true ).message( MessageConstant.ADD_CHECKGROUP_SUCCESS ).build();
    }

    /**
     * 分页查询
     *
     * @param queryPageBean 分页条件
     * @return 分页结果数据封装对象
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkGroupService.pageQuery( queryPageBean );
    }

    /**
     * 根据id查询检查组
     *
     * @param id 检查组id
     * @return 指定检查组信息
     */
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findById( id );
            //服务调用成功
            return Result.builder().flag( true ).message( MessageConstant.QUERY_CHECKGROUP_SUCCESS ).data( checkGroup ).build();
        } catch (Exception e) {
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.QUERY_SETMEAL_FAIL ).build();
        }
    }

    /**
     * 根据检查组id查询对应的所有检查项id
     *
     * @param id 检查组id
     * @return 指定检查组关联的所有检查项
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId/{id}")
    public Result findCheckItemIdsByCheckGroupId(@PathVariable("id") Integer id) {
        try {
            List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId( id );
            //服务调用成功
            return Result.builder().flag( true ).message( MessageConstant.QUERY_CHECKITEM_SUCCESS ).data( checkItemIds ).build();
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.QUERY_CHECKITEM_FAIL ).build();
        }
    }

    /**
     * 编辑检查组
     *
     * @param checkGroup   检查组信息
     * @param checkItemIds 与检查组关联的检查项id
     * @return 新增成功或者失败提示
     */
    @PutMapping("/edit/{checkItemIds}")
    public Result edit(@RequestBody CheckGroup checkGroup, @PathVariable("checkItemIds") Integer[] checkItemIds) {
        try {
            checkGroupService.edit( checkGroup, checkItemIds );
        } catch (Exception e) {
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.EDIT_CHECKGROUP_FAIL ).build();
        }
        //服务调用成功
        return Result.builder().flag( true ).message( MessageConstant.EDIT_CHECKGROUP_SUCCESS ).build();
    }

    /**
     * 删除检查组
     *
     * @param id 检查组id
     * @return 成功或失败对应提示
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        try {
            checkGroupService.deleteById( id );
        } catch (Exception e) {
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.DELETE_CHECKGROUP_FAIL ).build();
        }
        //服务调用成功
        return Result.builder().flag( true ).message( MessageConstant.DELETE_CHECKGROUP_SUCCESS ).build();
    }
}