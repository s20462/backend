package com.example.io_backend.controller;

import com.example.io_backend.model.MedicalInfo;
import com.example.io_backend.model.User;
import com.example.io_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public List<User> getAll() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody User user, @PathVariable String id) {
        userService.updateUser(user,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @PostMapping("/info/{id}")
    public void addMedicalInfo(@RequestBody MedicalInfo medicalInfo, @PathVariable String id) {
        userService.addUserMedicalInfo(id,medicalInfo);
    }

    @PutMapping("info/{id}")
    public void update(@RequestBody MedicalInfo medicalInfo, @PathVariable String id) {
        userService.updateUserMedicalInfo(id, medicalInfo);
    }
}
