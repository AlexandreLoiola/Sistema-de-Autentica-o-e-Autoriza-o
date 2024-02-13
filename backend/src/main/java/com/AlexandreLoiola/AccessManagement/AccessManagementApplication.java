package com.AlexandreLoiola.AccessManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AccessManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessManagementApplication.class, args);
	}
}
