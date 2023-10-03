package com.thoughtworks.ecombackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcomBackendApplication {
    public static void main(String[] args) {
        System.out.println("Hello World");
        SpringApplication.run(EcomBackendApplication.class, args);
    }
}
