package com.example.booksclientjava;

import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class GraphQLResponse {

    private ResponseEntity<String> responseEntity;

    public GraphQLResponse(ResponseEntity<String> responseEntity) {
        this.responseEntity = Objects.requireNonNull(responseEntity);
        Objects.requireNonNull(responseEntity.getBody(),
                () -> "Body is empty with status " + responseEntity.getStatusCodeValue());
    }

    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }
}
