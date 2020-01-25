package com.example.booksclientjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Consumer;

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

//			GraphQLResponse response1 = graphQLTemplate.perform("request.graphql");
//			System.out.println("data:" + response1.get("$.data"));
//
//			GraphQLResponse response2 = graphQLTemplate.perform("two-operation.graphql", "BooksWithTitle");
//			System.out.println("data:" + response2.get("$.data"));
//
//			GraphQLResponse response3 = graphQLTemplate.perform("two-operation.graphql", "BooksWithTitleAndAuthor");
//			System.out.println("data:" + response3.get("$.data"));
//
//			GraphQLResponse response4 = graphQLTemplate.perform("add-review.graphql");
//			System.out.println("data:" + response4.get("$.data"));

			List<GraphQLResponse> responses = graphQLTemplate.performMany(
					"add-review-with-variables.graphql", "AddReview", "add-review-variables.json");
			responses.forEach(response -> System.out.println("data:" + response.get("$.data")));


		};
	}
}
