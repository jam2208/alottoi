package com.example.alottoi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AlottoiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlottoiApplication.class, args);
	}

}
