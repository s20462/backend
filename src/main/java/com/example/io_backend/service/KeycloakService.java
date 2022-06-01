package com.example.io_backend.service;

import com.example.io_backend.auth.KeycloakRestTemplate;
import com.example.io_backend.auth.constants.KeycloakApiConstants;
import com.example.io_backend.exception.BadRequestException;
import com.example.io_backend.exception.InternalServerErrorException;
import com.example.io_backend.model.Staff;
import com.example.io_backend.model.dto.representations.RoleRepresentation;
import com.example.io_backend.model.dto.request.AuthAssignRoleRequest;
import com.example.io_backend.model.dto.request.CreateStaffUserRequest;
import com.example.io_backend.model.dto.request.CreateUserRequest;
import com.example.io_backend.model.dto.request.LoginRequest;
import com.example.io_backend.model.dto.representations.CredentialsRepresentation;
import com.example.io_backend.model.dto.representations.UserRepresentation;
import com.example.io_backend.auth.enums.CredentialType;
import com.example.io_backend.model.User;
import com.example.io_backend.repository.StaffRepository;
import com.example.io_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {
    private final KeycloakRestTemplate keycloakRestTemplate;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    @Value("${KEYCLOAK_URL}")
    private String BASE_URL;
    @Value("${KEYCLOAK_REALM}")
    private String REALM;

    public User createUser(CreateUserRequest createUserRequest) {
        log.info("Creating new user with data: " + createUserRequest);

        UserRepresentation userRepresentation = UserRepresentation
                .builder()
                .email(createUserRequest.getEmail())
                .firstName(createUserRequest.getFirstName())
                .lastName(createUserRequest.getLastName())
                .userName(createUserRequest.getUserName())
                .enabled(true)
                .credentials(
                        List.of(
                                CredentialsRepresentation
                                        .builder()
                                        .temporary(false)
                                        .type(CredentialType.PASSWORD)
                                        .value(createUserRequest.getPassword())
                                        .build()
                        )
                )
                .build();
        String URL = KeycloakApiConstants.createURL(BASE_URL + KeycloakApiConstants.USERS,"{REALM}", REALM);
        ResponseEntity<Void> keycloakResponse = keycloakRestTemplate.postForEntity(URL, userRepresentation, Void.class);

        if (keycloakResponse.getStatusCode() == HttpStatus.CREATED) {
            log.info("User created");
            ResponseEntity<UserRepresentation[]> usersResponse = keycloakRestTemplate.getForEntity(URL, UserRepresentation[].class);
            if (!usersResponse.getStatusCode().is2xxSuccessful()) {
                throw new InternalServerErrorException("Error creating user!");
            }
            var user =  Arrays.stream(Objects.requireNonNull(usersResponse.getBody())).filter(x -> x.equals(userRepresentation)).toList().get(0);

            User businessUser = User
                    .builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phone(createUserRequest.getPhoneNumber())
                    .bandCode(UUID.randomUUID().toString())
                    .birthDate(createUserRequest.getBirthDate())
                    .build();

            businessUser = userRepository.save(businessUser);

            return businessUser;
        } else if (keycloakResponse.getStatusCode() == HttpStatus.CONFLICT) {
            throw new BadRequestException("User exists with same username");
        }
        return null;
    }

    public Staff createUser(CreateStaffUserRequest createStaffUserRequest) {
        UserRepresentation userRepresentation = UserRepresentation
                .builder()
                .email(createStaffUserRequest.getEmail())
                .firstName(createStaffUserRequest.getFirstName())
                .lastName(createStaffUserRequest.getLastName())
                .userName(createStaffUserRequest.getUserName())
                .enabled(true)
                .credentials(
                        List.of(
                                CredentialsRepresentation
                                        .builder()
                                        .temporary(false)
                                        .type(CredentialType.PASSWORD)
                                        .value(createStaffUserRequest.getPassword())
                                        .build()
                        )
                )
                .build();

        String URL = KeycloakApiConstants.createURL(BASE_URL + KeycloakApiConstants.USERS,"{REALM}", REALM);
        ResponseEntity<Void> keycloakResponse = keycloakRestTemplate.postForEntity(URL, userRepresentation, Void.class);

        if (keycloakResponse.getStatusCode() == HttpStatus.CREATED) {
            log.info("User created");
            ResponseEntity<UserRepresentation[]> usersResponse = keycloakRestTemplate.getForEntity(URL, UserRepresentation[].class);
            if (!usersResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Error creating user!");
            }
            var user =  Arrays.stream(Objects.requireNonNull(usersResponse.getBody())).filter(x -> x.equals(userRepresentation)).toList().get(0);

            var roleResponse = keycloakRestTemplate.getForEntity(BASE_URL + KeycloakApiConstants.ROLES, RoleRepresentation[].class);
            if (!roleResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Cannot fetch roles");
            }
            List<RoleRepresentation> roles = Arrays.asList(Objects.requireNonNull(roleResponse.getBody()));
            List<RoleRepresentation> roleList = roles.stream().filter(x -> x.getName().equalsIgnoreCase(createStaffUserRequest.getStaffType().name())).toList();
            if (roleList.size() < 1) {
                throw new InternalServerErrorException("Role: " + createStaffUserRequest.getStaffType() + " is not on KC auth server!");
            }
            RoleRepresentation role = roleList.get(0);

            AuthAssignRoleRequest roleRequest = new AuthAssignRoleRequest();
            roleRequest.setRoleId(role.getId());
            roleRequest.setRoleName(role.getName());


            String url = BASE_URL + KeycloakApiConstants.ROLE_MAPPING;
            url = url.replace("{USER_ID}", user.getId());
            url = url.replace("{REALM}", REALM);
            var response = keycloakRestTemplate.postForEntity(url, List.of(roleRequest), Void.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new InternalServerErrorException("error mapping role to user");
            }

            User newUser = new User();
            newUser.setFirstName(createStaffUserRequest.getFirstName());
            newUser.setLastName(createStaffUserRequest.getLastName());
            newUser.setBirthDate(createStaffUserRequest.getBirthDate());
            newUser.setPhone(createStaffUserRequest.getPhone());
            newUser.setId(user.getId());
            newUser.setBandCode(null);
            newUser.setMedicalInfo(null);

            newUser = userRepository.save(newUser);

            Staff staff = new Staff();
            staff.setStaffType(createStaffUserRequest.getStaffType());
            staff.setBirthDate(createStaffUserRequest.getBirthDate());
            staff.setId(null);
            staff.setPhone(createStaffUserRequest.getPhone());
            staff.setUser(newUser);

            staff = staffRepository.save(staff);

            return staff;
        } else if (keycloakResponse.getStatusCode() == HttpStatus.CONFLICT) {
            throw new BadRequestException("User exists with same username");
        }
        return null;
    }

    public List<UserRepresentation> getUsers() {
        String URL = KeycloakApiConstants.createURL(BASE_URL + KeycloakApiConstants.USERS,"{REALM}", REALM);
        log.info("Calling: " + URL);
        ResponseEntity<UserRepresentation[]> usersResponse = keycloakRestTemplate.getForEntity(URL, UserRepresentation[].class);
        if (!usersResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("error");
        }

        return Arrays.asList(Objects.requireNonNull(usersResponse.getBody()));
    }

    public void deleteUser(UserRepresentation userRepresentation) {
        String URL = BASE_URL + KeycloakApiConstants.DELETE_USER.replace("{REALM}", REALM);
        URL = URL.replace("{USER_ID}", userRepresentation.getId());
        keycloakRestTemplate.delete(URL);
    }

    public String loginUser(LoginRequest loginRequest) {
        return keycloakRestTemplate.postForAccessToken(loginRequest);
    }

}
