package com.example.booksclientjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BooksClientJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksClientJavaApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	CommandLineRunner run(GraphQLTemplate graphQLTemplate) {
		return args -> {

			GraphQLResponse response = graphQLTemplate.perform("request.graphql", null);

			System.out.println("Books:" + response.getResponseEntity().getBody());

		};
	}
}
