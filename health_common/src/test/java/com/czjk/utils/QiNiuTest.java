package com.czjk.utils;

import org.junit.Test;

/**
 * @Author: Haotian
 * @Date: 2019/12/25 14:17
 * @Description: 七牛云上传测试
 */
public class QiNiuTest {
    @Test
    public void upload() {
        QiNiuUtils.uploadToQiNiu( "E:\\黑马资料\\传智健康资料\\day04\\素材\\图片资源\\ac3b5a4d-33a5-4f37-bd49-99e06ce17d202.jpg",
                "68c7c13f-8fc2-46c3-b5d6-f7ec7992dc6e1.jpg" );
    }

    @Test
    public void delete() {
        QiNiuUtils.deleteFileFromQiNiu( "Fqothp-T4D7zvrG-bibdLNGbGQjJ" );
    }
}