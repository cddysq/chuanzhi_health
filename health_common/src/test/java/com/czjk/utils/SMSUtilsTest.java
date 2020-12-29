package com.czjk.utils;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

/**
 * 阿里云短信测试
 *
 * @author Haotian
 * @version 2.0
 * @date 2020/12/29 10:17
 **/
public class SMSUtilsTest {
    @Test
    public void sendShortMessage() {
        // 接收手机号
        String phoneNumber = "12345678901";
        // 随机4位数验证码
        String code = RandomUtil.randomNumbers( 4 );
        // 发送测试
        SMSUtils.sendShortMessage( SMSUtils.VALIDATE_CODE, phoneNumber, code );
    }
}
