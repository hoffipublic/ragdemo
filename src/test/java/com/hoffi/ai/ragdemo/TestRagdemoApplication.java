package com.hoffi.ai.ragdemo;

import org.springframework.boot.SpringApplication;

public class TestRagdemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(RagdemoApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
