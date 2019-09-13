package com.ysyesilyurt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * For table db table creation and validation process of the app we use Liquibase and Hibernate-ddl-auto together.
 *
 * On startup, first liquibase creates tables without relations, if they do not already exist (check
 * db/mysql/changelog/dbchangelog.xml), and then hibernate updates the tables with their relations,
 * (spring.jpa.hibernate.ddl-auto: update)
 *
 * After that, on each run liquibase and hibernate just validates the tables, nothing more.
 *
 */

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}