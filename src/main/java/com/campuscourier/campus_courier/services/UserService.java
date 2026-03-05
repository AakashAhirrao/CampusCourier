package com.campuscourier.campus_courier.services;

import com.campuscourier.campus_courier.models.User;
import com.campuscourier.campus_courier.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Service layer is second layer in 3 tier architecture containing business logic
// @Service tells java that this class holds the service/brain logic of application
// this is the brain where logic of if else and conditions of the data is retrieved and processed
@Service
public class UserService {

    private final UserRepository userRepository;

    // This is called as dependency injection, where class is injected in field constructor and initialized
    // Spring boot strongly forbids created a new object from class
    // Dependency 
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(User user){
        // logic checks and legality checks
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
