package com.pengju;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author pengju
 */
@SpringBootApplication(scanBasePackages = "com.pengju")
@MapperScan("com/pengju/nasSystem/mapper")
public class NasApplication {

    public static void main(String[] args) {
        SpringApplication.run(NasApplication.class, args);
    }

}
