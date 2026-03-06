package com.campuscourier.campus_courier.services;

import com.campuscourier.campus_courier.models.Request;
import com.campuscourier.campus_courier.models.User;
import com.campuscourier.campus_courier.repositories.RequestRepository;
import com.campuscourier.campus_courier.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RequestService {

    private final UserRepository userRepository;
    private final RequestRepository requestRepository; // declaring interface to DI

    @Autowired
    public RequestService (UserRepository userRepository, RequestRepository  requestRepository){
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    public Request createRequest (Long userId, Request newRequest) {

        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Error: User with ID: "+userId+" does not exist"));
        // this is part of what is called as Defensive programming. which uses wrapper called Optional
        // we told userRepository to find the user with ID, if it exists then good or .orElseThrow() methods acts
        // as a strict bouncer and throw a clean error message

        newRequest.setRequester(requester); // here we are taking newRequest object of Request class
        // and using the setRequest setter inside Request class to set requester
        // in simple terms we are setting User with given ID to given request

        newRequest.setStatus("PENDING"); // setting status to PENDING as initial

        return requestRepository.save(newRequest);
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }
}
