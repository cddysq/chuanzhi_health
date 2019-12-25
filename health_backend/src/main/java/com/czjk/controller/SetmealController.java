package com.czjk.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.IdUtil;
import com.czjk.constant.MessageConstant;
import com.czjk.entity.Result;
import com.czjk.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @Author: Haotian
 * @Date: 2019/12/25 18:44
 * @Description: 体检套餐管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

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
        } catch (IOException e) {
            e.printStackTrace();
            //服务调用失败
            return Result.builder().flag( false ).message( MessageConstant.PIC_UPLOAD_FAIL ).build();
        }
        //服务调用成功
        return Result.builder().flag( true ).message( MessageConstant.PIC_UPLOAD_SUCCESS ).data( filename ).build();
    }
}
