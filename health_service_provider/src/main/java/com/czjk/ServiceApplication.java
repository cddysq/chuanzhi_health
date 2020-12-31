package com.czjk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * 服务端启动类
 *
 * @author Haotian
 * @version 2.0
 * @date 2020/12/29 10:39
 */
@Configuration
@SpringBootApplication
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run( ServiceApplication.class, args );
    }

}
