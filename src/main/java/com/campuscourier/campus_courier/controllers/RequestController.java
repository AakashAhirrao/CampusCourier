package com.campuscourier.campus_courier.controllers;

import com.campuscourier.campus_courier.models.Request;
import com.campuscourier.campus_courier.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController (RequestService requestService){
        this.requestService = requestService;
    }

    @PostMapping("/create/{userId}")
    public Request createRequest (@PathVariable Long userId, @RequestBody Request newRequest){
        // @PathVariable traps or catches the userId in curly braces converts it into the Long variable which is useful for seeing who
        // is creating the request
        return requestService.createRequest(userId, newRequest);
    }

    @GetMapping("/feed")
    public List<Request> getFeed(){
        return requestService.getPendingRequests();
    }

    @PutMapping("{requestId}/accept/{delivererId}")
    public Request acceptRequest(@PathVariable Long requestId, @PathVariable Long delivererId) {
        return requestService.acceptRequest(requestId, delivererId);
    }

    @PutMapping("/{requestId}/complete/{delivererId}")
    public Request completeRequest(@PathVariable Long requestId, @PathVariable Long delivererId){
        return requestService.completeRequest(requestId, delivererId);
    }
}
