package com.example.io_backend.exception.handler;


import com.example.io_backend.exception.BadRequestException;
import com.example.io_backend.exception.GarryException;
import com.example.io_backend.exception.InternalServerErrorException;
import com.example.io_backend.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    private final Map<String, Object> errorMsg = new LinkedHashMap<>();

    @ExceptionHandler(GarryException.class)
    public ResponseEntity<Object> handleError(GarryException e) {
        Map<String, Object> errors = new LinkedHashMap<>();
        errors.put("status", e.getStatus());
        errors.put("msg", e.getMessage());

        return new ResponseEntity<>(errors, (HttpStatus)errors.get("status"));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleError(BadRequestException e) {
        errorMsg.clear();
        errorMsg.put("status", HttpStatus.BAD_REQUEST);
        errorMsg.put("msg", e.getMessage());

        return errorMsg;
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleError(InternalServerErrorException e) {
        errorMsg.clear();
        errorMsg.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        errorMsg.put("msg", e.getMessage());

        return errorMsg;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleError(NotFoundException e) {
        errorMsg.clear();
        errorMsg.put("status", HttpStatus.NOT_FOUND);
        errorMsg.put("msg", e.getMessage());

        return errorMsg;
    }
}
