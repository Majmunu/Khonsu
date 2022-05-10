package com.halo.khonsu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RestController;
@RestController

@EnableCaching
@SpringBootApplication
public class KhonsuApplication {


    public static void main(String[] args) {
        SpringApplication.run(KhonsuApplication.class, args);
    }


}
