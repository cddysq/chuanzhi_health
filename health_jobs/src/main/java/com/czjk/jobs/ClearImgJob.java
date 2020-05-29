package com.czjk.jobs;

import cn.hutool.core.collection.CollUtil;
import com.czjk.constant.RedisConstant;
import com.czjk.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 两小时清理一次七牛垃圾图片
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/29 17:12
 **/
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
        Set<String> sdiff = jedisPool.getResource().sdiff( RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES );
        //判断是否存在差积
        if (CollUtil.isNotEmpty( sdiff )) {
            for (String picName : sdiff) {
                //删除七牛云服务器上的图片
                QiNiuUtils.deleteFileFromQiNiu( picName );
                //从Redis集合中删除图片名称
                jedisPool.getResource().
                        srem( RedisConstant.SETMEAL_PIC_RESOURCES, picName );
            }
        }
    }
}