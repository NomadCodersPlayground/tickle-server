package com.techblogfinder.api;

import com.techblogfinder.api.article.infrastructure.ElasticArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableJpaAuditing
@EnableElasticsearchRepositories
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		log.info("print database url: {}", System.getenv("DATABASE_URL"));
		log.info("print elasticsearch url: {}", System.getenv("ELASTICSEARCH_URL"));
		SpringApplication.run(ApiApplication.class, args);
	}

}
