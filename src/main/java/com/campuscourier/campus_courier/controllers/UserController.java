package com.campuscourier.campus_courier.controllers;

import com.campuscourier.campus_courier.models.User;
import com.campuscourier.campus_courier.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller is the first layer in 3 tier architecture which contains all the REST APIs
// Controller listen and handle all the requests from user and internet
// @RestController annotation indicates that this class is allowed to listen and get request from internet
@RestController
@RequestMapping("/api/users") // base url for everything in this file
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService){
        this.userService = userService;
    }

    // Endpoint 1: Add a new user to the database
    @PostMapping("/register") // Post is used to send data or make changes in database
    public User registerUser(@RequestBody User user){ // here @RequestBody is important as data comes in JSON format,
        // @RequestBody takes JSON data and automatically packs it into User object and sends it to lower layer
        // @RequestBody uses getter and setter in User to fit JSON data into the object
        return userService.registerUser(user);
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }
}
