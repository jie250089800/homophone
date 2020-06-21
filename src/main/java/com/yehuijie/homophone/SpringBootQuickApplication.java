package com.yehuijie.homophone;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author huijieye
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.yehuijie.homophone.mapper*"})
public class SpringBootQuickApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootQuickApplication.class, args);
    }

}
