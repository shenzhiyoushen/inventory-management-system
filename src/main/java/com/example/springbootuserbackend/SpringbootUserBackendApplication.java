package com.example.springbootuserbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@MapperScan("com.example.springbootuserbackend.mapper")
public class SpringbootUserBackendApplication {
    public static void main(String[] args) {
        // SpringApplication.run(SpringbootUserBackendApplication.class, args);

        ConfigurableApplicationContext context = SpringApplication.run(SpringbootUserBackendApplication.class, args);

    }
}