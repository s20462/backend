package com.example.io_backend.exception;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.authorization.client.util.Http;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GarryException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public GarryException() {
        super();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = "Unspecified error";
    }

    public GarryException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
