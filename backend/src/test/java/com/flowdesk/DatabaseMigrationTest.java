package com.flowdesk;
import org.junit.jupiter.api.Test;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.jdbc.core.JdbcTemplate;import org.springframework.test.context.DynamicPropertyRegistry;import org.springframework.test.context.DynamicPropertySource;import org.testcontainers.containers.PostgreSQLContainer;import org.testcontainers.junit.jupiter.Container;import org.testcontainers.junit.jupiter.Testcontainers;import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest @Testcontainers(disabledWithoutDocker=true) class DatabaseMigrationTest {
 @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
 @DynamicPropertySource static void database(DynamicPropertyRegistry r){r.add("spring.datasource.url",POSTGRES::getJdbcUrl);r.add("spring.datasource.username",POSTGRES::getUsername);r.add("spring.datasource.password",POSTGRES::getPassword);}
 @Autowired JdbcTemplate jdbc;
 @Test void flywayCreatesRolesAndCategories(){assertEquals(4,jdbc.queryForObject("select count(*) from roles",Integer.class));assertEquals(8,jdbc.queryForObject("select count(*) from categories",Integer.class));}
}
