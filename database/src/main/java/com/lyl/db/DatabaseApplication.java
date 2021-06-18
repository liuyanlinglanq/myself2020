package com.lyl.db;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * description com.lyl.db.DatabaseApplication
 *
 * @author liuyanling
 * @date 2021-05-10 16:22
 */
@SpringBootApplication
@MapperScan("com.lyl.db.mapper")
@NacosPropertySource(dataId = "com.xes.columbus.kbs.test", groupId = "knowledge-base", autoRefreshed = true, type = ConfigType.YAML)
@EnableDiscoveryClient
public class DatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }
}

