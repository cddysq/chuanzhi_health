package com.czjk.jobs;

import cn.hutool.core.collection.CollUtil;
import com.czjk.constant.RedisConstant;
import com.czjk.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @Author: Haotian
 * @Date: 2019/12/26 19:20
 * @Description: ；两小时清理一次七牛垃圾图片
 */
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