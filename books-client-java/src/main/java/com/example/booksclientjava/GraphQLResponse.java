package com.example.booksclientjava;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class GraphQLResponse {

    private ResponseEntity<String> responseEntity;
    private ReadContext readContext;

    public GraphQLResponse(ResponseEntity<String> responseEntity) {
        this.responseEntity = Objects.requireNonNull(responseEntity);
        Objects.requireNonNull(responseEntity.getBody(),
                () -> "Body is empty with status " + responseEntity.getStatusCodeValue());
        readContext = JsonPath.parse(responseEntity.getBody());
    }

    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }

    public Object get(String path) {
        return readContext.read(path);
    }
}
