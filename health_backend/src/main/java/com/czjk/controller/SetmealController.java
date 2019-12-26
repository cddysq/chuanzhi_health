package com.czjk.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.constant.MessageConstant;
import com.czjk.constant.RedisConstant;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.entity.Result;
import com.czjk.pojo.Setmeal;
import com.czjk.service.SetmealService;
import com.czjk.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Objects;

/**
 * @Author: Haotian
 * @Date: 2019/12/25 18:44
 * @Description: 体检套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 文件上传
     *
     * @param imgFile 前端发送来的文件
     * @return 上传七牛云成功与否
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //获取原始文件名
        String originalFilename = imgFile.getOriginalFilename();
        //拆分字符串获取文件后缀
        String type = Objects.requireNonNull( originalFilename ).substring( originalFilename.lastIndexOf( "." ) );
        //创建UUID.文件类型新文件名防止覆盖 类似于：b17f24ff026d40949c85a24f4f375d42.jpg
        String filename = IdUtil.simpleUUID() + type;
        try {
            //将文件上传到七牛云服务器
            QiNiuUtils.uploadToQiNiu( imgFile.getBytes(), filename );
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd( RedisConstant.SETMEAL_PIC_RESOURCES, filename );
        } catch (IOException e) {
            e.printStackTrace();
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.PIC_UPLOAD_FAIL ).build();
        }
        //服务调用成功
        return Result.builder().flag( true ).message( MessageConstant.PIC_UPLOAD_SUCCESS ).data( filename ).build();
    }

    /**
     * 新增体检套餐
     *
     * @param setmeal       套餐信息
     * @param checkGroupIds 检查组id集合
     * @return 新增是够成功
     */
    @PostMapping("/add/{checkGroupIds}")
    public Result add(@RequestBody Setmeal setmeal, @PathVariable("checkGroupIds") Integer[] checkGroupIds) {
        try {
            setmealService.add( setmeal, checkGroupIds );
        } catch (Exception e) {
            //新增套餐失败
            return Result.builder().flag( false ).message( MessageConstant.ADD_SETMEAL_FAIL ).build();
        }
        //新增套餐成功
        return Result.builder().flag( true ).message( MessageConstant.ADD_SETMEAL_SUCCESS ).build();
    }

    /**
     * 分页查询
     *
     * @param queryPageBean 分页条件
     * @return 分页结果数据封装对象
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.pageQuery( queryPageBean );
    }
}