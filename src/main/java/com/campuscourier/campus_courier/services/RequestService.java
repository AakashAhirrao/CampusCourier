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

    public List<Request> getPendingRequests() {
        return requestRepository.findByStatus("PENDING");
    }

    public Request acceptRequest(Long requestId, Long delivererId) {

        // First step is to find the request
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Request with ID " + requestId + " not found"
                ));

        // Check: the request is actually pending
        if (!request.getStatus().equals("PENDING")){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Sorry, the request is already taken"
            );
        }

        User deliverer = userRepository.findById(delivererId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        request.setStatus("ACCEPTED");
        request.setDeliverer(deliverer);

        return requestRepository.save(request);
    }

    public Request completeRequest(Long requestId, Long delivererId) {

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Request with ID " + requestId + " not found"
                ));

        if (!request.getStatus().equals("ACCEPTED")){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "You can only complete request that are accepted"
            );
        }

        if (!request.getDeliverer().getId().equals(delivererId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Security alert: only assigned deliverer can complete this request"
            );
        }

        request.setStatus("COMPLETED");

        return requestRepository.save(request);
    }
}
