package com.bezkoder.spring.jpa.h2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Spring Boot application.
 * This class serves as the entry point for the Spring Boot application.
 * It sets up the application context and launches the Spring Boot application.
 */

@SpringBootApplication
public class SpringBootJpaH2Application {
/**
 * Main method that starts the Spring Boot application.
 *
 * @param args Command line arguments passed to the application.
 */
public static void main(String[] args) {
SpringApplication.run(SpringBootJpaH2Application.class, args);
}

}
