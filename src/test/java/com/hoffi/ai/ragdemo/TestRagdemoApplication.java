package com.hoffi.ai.ragdemo;

import org.springframework.boot.SpringApplication;
/** see https://spring.io/blog/2023/06/23/improved-testcontainers-support-in-spring-boot-3-1
 * ./gradlew bootTestRun */
public class TestRagdemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(RagdemoApplication::main)
            .with(TestcontainersConfiguration.class)
            .run(args);
    }

}
