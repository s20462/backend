package com.example.io_backend.service;

import com.example.io_backend.model.dto.UserMedicalInfoDto;
import com.example.io_backend.exception.NotFoundException;
import com.example.io_backend.model.MedicalInfo;
import com.example.io_backend.model.User;
import com.example.io_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final KeycloakService kcService;
    private final UserRepository userRepository;
    private final MedicalInfoService medicalInfoService;
    private final ModelMapper modelMapper;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("No record with that id"));
    }

    public void updateUser(User user, String userId) {
        var p = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        p.setId(user.getId());
        p.setLastName(user.getLastName());
        p.setFirstName(user.getFirstName());
        p.setBirthDate(user.getBirthDate());
        p.setMedicalInfo(user.getMedicalInfo());
        p.setPhone(user.getPhone());
        p.setBandCode(user.getBandCode());

        userRepository.save(p);
    }

    public void deleteUser(String userId) {
        var p = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Person not found"));
        try {
            kcService.deleteUser(p.getId());
        } catch (Exception ignored){}
        userRepository.delete(p);
    }

    public UserMedicalInfoDto addUserMedicalInfo(String userId,@NotNull MedicalInfo medicalInfo) {
            User user = this.getUserById(userId);
            medicalInfoService.addMedicalInfo(medicalInfo);
            user.setMedicalInfo(medicalInfo);
        return modelMapper.map(user,UserMedicalInfoDto.class);
    }

    public UserMedicalInfoDto updateUserMedicalInfo(String userId, MedicalInfo medicalInfo ) {
        User user = this.getUserById(userId);
        medicalInfoService.updateMedicalInfo(medicalInfo,user.getMedicalInfo().getId());
        return modelMapper.map(user,UserMedicalInfoDto.class);
    }



}
