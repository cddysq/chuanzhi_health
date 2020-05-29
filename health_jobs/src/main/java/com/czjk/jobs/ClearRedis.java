package com.czjk.jobs;

import com.czjk.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

/**
 * 一月一次清理redis内存
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/29 17:12
 **/
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