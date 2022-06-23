package com.example.io_backend;

import com.example.io_backend.controller.UserController;
import com.example.io_backend.model.User;
import com.example.io_backend.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
public class UserControllerTests {

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    @Test
    public void notNull() {
        assertNotEquals(null, userController);
    }

    @Test
    public void empty() {
        assert !userController.getAll().isEmpty();
    }

    @Test
    public void putUser() {
        //var user = userController.add(new User(null, "Test", "Test", "test", "test", "testmail", new Date(), "0", "0", null));

        //assertEquals("Test", user.getFirstName());
    }

    @Test
    public void getUser() {
        //for(User user : userRepository.findAll()) {
        //    assertEquals(userController.getById(user.getId()).getId(), user.getId());
        //}
    }
}
