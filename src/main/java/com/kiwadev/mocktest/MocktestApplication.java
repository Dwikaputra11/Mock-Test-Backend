package com.kiwadev.mocktest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan("com.kiwadev.mocktest.models.domain")
public class MocktestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MocktestApplication.class, args);
	}

}
