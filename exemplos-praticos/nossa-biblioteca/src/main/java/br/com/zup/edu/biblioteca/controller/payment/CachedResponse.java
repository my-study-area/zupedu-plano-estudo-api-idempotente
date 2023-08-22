package br.com.zup.edu.biblioteca.controller.payment;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CachedResponse {
    private HttpStatus statusCode;
    private HttpHeaders headers;
    private Object body;

    public CachedResponse() {
    }

    public CachedResponse(HttpStatus statusCode, HttpHeaders headers, Object body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public CachedResponse toResponse(ResponseEntity<?> responseEntity) {
        return new CachedResponse(
                responseEntity.getStatusCode(),
                responseEntity.getHeaders(),
                responseEntity.getBody()
        );
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
