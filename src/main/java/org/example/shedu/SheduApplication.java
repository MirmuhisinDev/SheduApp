package org.example.shedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SheduApplication {

    public static void main(String[] args) {
        SpringApplication.run(SheduApplication.class, args);
    }

}
