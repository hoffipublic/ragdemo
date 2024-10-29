package com.hoffi.ai.ragdemo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/** see https://spring.io/blog/2023/06/23/improved-testcontainers-support-in-spring-boot-3-1 */
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {
    public static String postgresContainer_db_username = "testUser";
    public static String postgresContainer_db_password = "testSecret";
    public static String postgresContainer_db_dbname = "unittestdb";

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> pgvectorContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("pgvector/pgvector:pg16"))
            .withReuse(true);
    }

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        // by default testcontainers create a schema: 'test' with username: 'test' and password: 'test'
        System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.err.println("HERE HERE HERE HERE");
        PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"))
            //.withInitScript("init_postgresql.sql")
            .withUsername(postgresContainer_db_username)
            .withPassword(postgresContainer_db_password)
            .withDatabaseName(postgresContainer_db_dbname)
            .withReuse(true);
        //System.err.println(postgresContainer.getJdbcUrl());
        System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        return postgresContainer;
    }

//    @Bean
//    JdbcConnectionDetails myJdbcConnectionDetails() {
//        return new JdbcConnectionDetails() {
//            @Override
//            public String getUsername() {
//                return "myuser";
//            }
//
//            @Override
//            public String getPassword() {
//                return "secret";
//            }
//
//            @Override
//            public String getJdbcUrl() {
//                return "jdbc:postgresql://postgres-server.svc.local:5432/mydatabase?ssl=true&sslmode=required";
//            }
//        };
//    }
}
