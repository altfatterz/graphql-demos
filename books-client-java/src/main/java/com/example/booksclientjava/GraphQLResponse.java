package com.example.booksclientjava;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.springframework.http.ResponseEntity;

import static java.util.Objects.requireNonNull;

public class GraphQLResponse {

    private ResponseEntity<String> responseEntity;
    private ReadContext readContext;

    public GraphQLResponse(ResponseEntity<String> responseEntity) {
        this.responseEntity = requireNonNull(responseEntity);
        requireNonNull(responseEntity.getBody(), () -> "Body is empty with status " + responseEntity.getStatusCodeValue());
        this.readContext = JsonPath.parse(responseEntity.getBody());
    }

    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }

    public Object get(String path) {
        return readContext.read(path);
    }
}
