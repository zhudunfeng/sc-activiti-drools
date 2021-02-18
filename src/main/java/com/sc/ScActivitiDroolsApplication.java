package com.sc;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.sc")
public class ScActivitiDroolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScActivitiDroolsApplication.class, args);
    }

}
