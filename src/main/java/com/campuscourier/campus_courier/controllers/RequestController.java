package com.campuscourier.campus_courier.controllers;

import com.campuscourier.campus_courier.models.Request;
import com.campuscourier.campus_courier.models.User;
import com.campuscourier.campus_courier.repositories.UserRepository;
import com.campuscourier.campus_courier.services.RequestService;
import com.campuscourier.campus_courier.services.UserService;
import org.hibernate.boot.model.process.internal.UserTypeResolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;
    private final UserRepository userRepository;

    @Autowired
    public RequestController (RequestService requestService, UserRepository userRepository){
        this.requestService = requestService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Request createRequest (Principal principal, @RequestBody Request newRequest){
        // @PathVariable traps or catches the userId in curly braces converts it into the Long variable which is useful for seeing who
        // is creating the request

        // 2. The 'principal' holds the email from the user's JWT ID card.
        // In the future, we will use this email to find their real database ID.
        String userEmail = principal.getName();
        System.out.println("New order received from: " + userEmail);

        // 3. For right now, just to prove the connection works,
        // let's temporarily hardcode the userId as 1 so your service doesn't break.
        User actualUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Error: Student not found"));

        Long realUserId = actualUser.getId();

        return requestService.createRequest(realUserId, newRequest);
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
