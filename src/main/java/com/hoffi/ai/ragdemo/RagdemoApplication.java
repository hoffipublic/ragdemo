package com.hoffi.ai.ragdemo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RagdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RagdemoApplication.class, args);
    }

    @Bean
    ApplicationRunner appStarted() {
        return args -> {
            System.out.println("spring.ai.openai.api-key:      '" + System.getenv("spring__ai__openai__api_key") + "'");
            System.out.println("spring.ai.openai.chat.api-key: '" + System.getenv("spring__ai__openai__chat__api_key") + "'");
        };
    }

}
