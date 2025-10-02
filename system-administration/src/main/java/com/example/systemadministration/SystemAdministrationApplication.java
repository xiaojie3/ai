package com.example.systemadministration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing // 关键注解：启用 JPA 审计
public class SystemAdministrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemAdministrationApplication.class, args);
    }

}
