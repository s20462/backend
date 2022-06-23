package com.example.io_backend.dto;

import com.example.io_backend.model.MedicalInfo;
import com.example.io_backend.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    String id;
    String firstName;
    String lastName;
    String password;
    String email;
    LocalDate birthDate;
    String phone;
    String bandCode;
    MedicalInfo medicalInfo;

    public static UserDto of(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(userDto.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());

        return userDto;
    }
}
