package com.czjk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.entity.Result;
import com.czjk.pojo.CheckItem;
import com.czjk.service.CheckItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            e.printStackTrace();
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
}