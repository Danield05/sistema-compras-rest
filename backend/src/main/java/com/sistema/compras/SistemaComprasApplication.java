package com.sistema.compras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.sistema.compras")
public class SistemaComprasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaComprasApplication.class, args);
    }
}