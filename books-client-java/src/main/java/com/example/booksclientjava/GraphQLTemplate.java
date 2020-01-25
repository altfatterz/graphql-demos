package com.example.booksclientjava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class GraphQLTemplate {

    @Value("${graphql.endpoint.url}")
    private String graphqlEndpointUrl;

    private final RestTemplate restTemplate;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GraphQLTemplate(RestTemplate restTemplate, ResourceLoader resourceLoader) {
        this.restTemplate = restTemplate;
        this.resourceLoader = resourceLoader;
    }

    public GraphQLResponse perform(String graphQLRequest) throws IOException {
        String graphql = load(graphQLRequest);
        String payload = createJsonQuery(graphql, null);
        return post(payload);
    }

    public GraphQLResponse perform(String graphQLRequest, String operationName) throws IOException {
        String graphql = load(graphQLRequest);
        String payload = createJsonQuery(graphql, operationName);
        return post(payload);
    }

    public List<GraphQLResponse> performMany(String graphQLRequest, String operationName, String variablesLocation) throws IOException {
        String graphql = load(graphQLRequest);
        String variables = load(variablesLocation);
        List<String> jsonQueries = createJsonQueries(graphql, operationName, variables);
        List<GraphQLResponse> responses = new ArrayList<>();
        jsonQueries.forEach(jsonQuery -> {
            GraphQLResponse response = post(jsonQuery);
            responses.add(response);
        });
        return responses;
    }

    private String createJsonQuery(String graphql, String operationName) throws JsonProcessingException {
        ObjectNode wrapper = objectMapper.createObjectNode();
        wrapper.put("query", graphql);
        wrapper.put("operationName", operationName);
        return objectMapper.writeValueAsString(wrapper);
    }

    private List<String> createJsonQueries(String graphql, String operationName, String variables) throws JsonProcessingException {
        ObjectNode wrapper = objectMapper.createObjectNode();
        wrapper.put("query", graphql);
        wrapper.put("operationName", operationName);

        List<String> queries = new ArrayList<>();

        JsonNode jsonNode = objectMapper.readTree(variables);
        for (int i = 0; i < jsonNode.size(); i++) {
            wrapper.set("variables", jsonNode.get(i));
            queries.add(objectMapper.writeValueAsString(wrapper));
        }

        return queries;
    }

    private String load(String location) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + location);
        return loadResource(resource);
    }

    private String loadResource(Resource resource) throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
    }

    private GraphQLResponse post(String payload) {
        return postRequest(RequestFactory.forJson(payload, new HttpHeaders()));
    }

    private GraphQLResponse postRequest(HttpEntity<Object> request) {
        ResponseEntity<String> response = restTemplate.exchange(graphqlEndpointUrl, HttpMethod.POST, request, String.class);
        return new GraphQLResponse(response);
    }

}
