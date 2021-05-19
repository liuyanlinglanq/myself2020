package com.lyl.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description com.lyl.db.DatabaseApplication
 *
 * @author liuyanling
 * @date 2021-05-10 16:22
 */
@SpringBootApplication
@MapperScan("com.lyl.db.mapper")
public class DatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }
}

