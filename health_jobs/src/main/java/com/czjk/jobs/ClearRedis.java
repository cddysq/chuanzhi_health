package com.czjk.jobs;

import cn.hutool.core.collection.CollUtil;
import com.czjk.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @Author: Haotian
 * @Date: 2019/12/26 19:45
 * @Description: 一月一次清理redis内存
 */
public class ClearRedis {
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private ClearImgJob clearImgJob;

    public void clearRedis() {
        //先清理垃圾图片
        clearImgJob.clearImg();

        //删除存储的所有永久图片名
        jedisPool.getResource().del( RedisConstant.SETMEAL_PIC_DB_RESOURCES );

        //删除临时图片名集合
        jedisPool.getResource().del( RedisConstant.SETMEAL_PIC_RESOURCES );
    }
}