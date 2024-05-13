package com.techblogfinder.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		log.info("print elasticsearch url: {}", System.getenv("ELASTICSEARCH_URL"));
		SpringApplication.run(ApiApplication.class, args);
	}

}
