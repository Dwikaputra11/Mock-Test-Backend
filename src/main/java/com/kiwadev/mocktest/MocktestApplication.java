package com.kiwadev.mocktest;

import com.kiwadev.mocktest.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan("com.kiwadev.mocktest.models.domain")
@EnableConfigurationProperties(ApplicationProperties.class)
public class MocktestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MocktestApplication.class, args);
	}

}
