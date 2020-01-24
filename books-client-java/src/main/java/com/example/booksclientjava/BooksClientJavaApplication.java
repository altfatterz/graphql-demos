package com.example.booksclientjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.jar.JarOutputStream;

@SpringBootApplication
public class BooksClientJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksClientJavaApplication.class, args);
	}

	private final String query = "query Books { books { title } }";

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	CommandLineRunner run(RestTemplate restTemplate) {
		return args -> {
			// https://github.com/graphql-java-kickstart/graphql-spring-boot/blob/master/graphql-spring-boot-test/src/main/java/com/graphql/spring/boot/test/GraphQLTestTemplate.java

			System.out.println("Send here GraphQL request");
		};
	}

}
