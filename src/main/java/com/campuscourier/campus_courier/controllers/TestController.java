package com.campuscourier.campus_courier.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


// Controller is the first layer in 3 tier architecture which contains all the REST APIs
// Controller listen and handle all the requests from user and internet
// @RestController annotation indicates that this class is allowed to listen and get request from internet
@RestController
public class TestController {
    @GetMapping("/hello") // @GetMapping("/hello") handles all the requests starting as localhost:8080/hello
    public String sayHello() {
        return "Hello from the Campus Courier Server!";
    }
}
