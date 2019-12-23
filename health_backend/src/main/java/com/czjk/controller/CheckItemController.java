package com.czjk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.entity.Result;
import com.czjk.pojo.CheckItem;
import com.czjk.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Haotian
 * @Date: 2019/12/20 19:29
 * @Description: 体检检查项管理
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference //查找服务
    private CheckItemService checkItemService;

    /**
     * 新增检查项
     *
     * @param checkitem 检查项pojo
     * @return 成功或失败对应提示
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkitem) {
        try {
            checkItemService.add( checkitem );
        } catch (Exception e) {
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.ADD_CHECKGROUP_FAIL ).build();
        }
        //服务调用成功
        return Result.builder().flag( true ).message( MessageConstant.ADD_CHECKGROUP_SUCCESS ).build();
    }

    /**
     * 分页查询
     *
     * @param queryPageBean 分页条件
     * @return 分页结果数据封装对象
     */
    @PostMapping("/findPage")
    public PageResult add(@RequestBody QueryPageBean queryPageBean) {
        return checkItemService.pageQuery( queryPageBean );
    }

    /**
     * 删除检查项
     *
     * @param id 检查项id
     * @return 成功或失败对应提示
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        try {
            checkItemService.deleteById( id );
        } catch (RuntimeException e) {
            //存在关联关系回显
            return Result.builder().flag( false ).message( e.getMessage() ).build();
        } catch (Exception e) {
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.DELETE_CHECKITEM_FAIL ).build();
        }
        //服务调用成功
        return Result.builder().flag( true ).message( MessageConstant.DELETE_CHECKITEM_SUCCESS ).build();
    }

    /**
     * 编辑检查项
     *
     * @param checkitem 检查项信息
     * @return 成功或失败对应提示
     */
    @PutMapping("/edit")
    public Result edit(@RequestBody CheckItem checkitem) {
        try {
            checkItemService.edit( checkitem );
        } catch (Exception e) {
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.EDIT_CHECKITEM_FAIL ).build();
        }
        //服务调用成功
        return Result.builder().flag( true ).message( MessageConstant.EDIT_CHECKITEM_SUCCESS ).build();
    }

    /**
     * 根据id查询检查项
     *
     * @param id 检查项id
     * @return 指定检查项信息
     */
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        try {
            CheckItem checkItem = checkItemService.findById( id );
            //服务调用成功
            return Result.builder().flag( true ).message( MessageConstant.QUERY_CHECKITEM_SUCCESS ).data( checkItem ).build();
        } catch (Exception e) {
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.QUERY_CHECKITEM_FAIL ).build();
        }
    }
}