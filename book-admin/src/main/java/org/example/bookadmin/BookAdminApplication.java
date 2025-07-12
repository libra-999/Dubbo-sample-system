package org.example.bookadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.example.bookadmin", "org.example.commonservice"})
@MapperScan("org.example.bookadmin.mapper")
@SpringBootConfiguration
public class BookAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookAdminApplication.class, args);

    }

}
