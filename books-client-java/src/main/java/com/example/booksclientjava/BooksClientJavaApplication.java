package com.example.booksclientjava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class BooksClientJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksClientJavaApplication.class, args);
	}

	private String ENDPOINT_URL = "http://localhost:8080/graphql";

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	CommandLineRunner run(RestTemplate restTemplate, ResourceLoader resourceLoader) {
		return args -> {
			// https://github.com/graphql-java-kickstart/graphql-spring-boot/blob/master/graphql-spring-boot-test/src/main/java/com/graphql/spring/boot/test/GraphQLTestTemplate.java

			System.out.println("query:" + loadQuery(resourceLoader, "request.graphql"));

			String books = perform(restTemplate, resourceLoader, "request.graphql");

			System.out.println("Books:" + books);

		};
	}

	public String perform(RestTemplate restTemplate, ResourceLoader resourceLoader, String graphqlResource) throws IOException {
		String graphql = loadQuery(resourceLoader, graphqlResource);
		String payload = createJsonQuery(graphql);
		return post(restTemplate, payload);
	}

	private String post(RestTemplate restTemplate, String payload) {
		return postRequest(restTemplate, RequestFactory.forJson(payload, new HttpHeaders()));
	}

	private String createJsonQuery(String graphql) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode wrapper = objectMapper.createObjectNode();
		wrapper.put("query", graphql);
		return objectMapper.writeValueAsString(wrapper);
	}

	private String postRequest(RestTemplate restTemplate, HttpEntity<Object> request) {
		ResponseEntity<String> response = restTemplate.exchange(ENDPOINT_URL, HttpMethod.POST, request, String.class);
		return response.getBody();
	}

	private String loadQuery(ResourceLoader resourceLoader, String location) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:" + location);
		return loadResource(resource);
	}

	private String loadResource(Resource resource) throws IOException {
		try (InputStream inputStream = resource.getInputStream()) {
			return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		}
	}
}
