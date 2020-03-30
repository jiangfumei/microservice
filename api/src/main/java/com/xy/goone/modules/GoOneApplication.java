package com.xy.goone.modules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author fumei.jiang
 * @date 2019-08-06 13:49
 */
//启用缓存
@EnableCaching
//启用自带定时任务
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@PropertySource(value= {"classpath:config.properties","classpath:application.yml"})
public class GoOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoOneApplication.class, args);
    }

}
