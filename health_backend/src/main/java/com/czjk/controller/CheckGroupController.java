package com.czjk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.entity.Result;
import com.czjk.pojo.CheckGroup;
import com.czjk.service.CheckGroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param checkitemIds 与检查组关联的检查项id
     * @return 新增成功或者失败提示
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.add( checkGroup, checkitemIds );
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
}