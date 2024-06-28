package com.arekproject.studiaprojekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StudiaProjektApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudiaProjektApplication.class, args);
    }

}
