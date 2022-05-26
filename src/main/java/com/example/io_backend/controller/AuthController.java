package com.example.io_backend.controller;

import com.example.io_backend.exception.GarryException;
import com.example.io_backend.model.Staff;
import com.example.io_backend.model.User;
import com.example.io_backend.model.dto.request.CreateStaffUserRequest;
import com.example.io_backend.model.dto.request.CreateUserRequest;
import com.example.io_backend.model.dto.request.LoginRequest;
import com.example.io_backend.service.KeycloakService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final KeycloakService keycloakService;

    @PostMapping("/login")
    @Operation(
            method = "GET",
            summary = "Gets JWT token for given username / password pair")
    public ResponseEntity<String> login(
            @Valid
            @Parameter(description = "Login credentials")
            @RequestBody LoginRequest loginRequest) {
        log.info("User with username: " + loginRequest.getUsername() + " attempted to login");
        String token;
       try {
           token = keycloakService.loginUser(loginRequest);
       } catch (HttpClientErrorException e) {
           throw new GarryException("Invalid username / password", e.getStatusCode());
       }

        if (token == null) {
            throw new GarryException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else return ResponseEntity.ok(token);
    }

    @PostMapping("/register/normal")
    @Operation(
            method = "POST",
            summary = "registers new generic user")
    public ResponseEntity<User> registerUser(
            @Valid
            @Parameter(description = "New generic user data")
            @RequestBody CreateUserRequest createUserRequest) {
        var user = keycloakService.createUser(createUserRequest);

        return user == null ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(user);
    }

    @PostMapping("/register/staff")
    @Operation(
            method = "POST",
            summary = "Registers new specialised user"
    )
    public ResponseEntity<Staff> registerStaffUser(
            @Valid
            @Parameter(description = "New specialized user data")
            @RequestBody CreateStaffUserRequest createStaffUserRequest) {
        var staffUser = keycloakService.createUser(createStaffUserRequest);

        return staffUser == null ?
                ResponseEntity.internalServerError().build() :
                ResponseEntity.ok(staffUser);

    }
}
