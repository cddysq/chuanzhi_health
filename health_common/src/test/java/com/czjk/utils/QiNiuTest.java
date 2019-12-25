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
        QiNiuUtils.uploadToQiNiu( "E:\\图片\\Hatsune Miku.jpg", null );
    }

    @Test
    public void delete() {
        QiNiuUtils.deleteFileFromQiNiu( "Fqothp-T4D7zvrG-bibdLNGbGQjJ" );
    }
}