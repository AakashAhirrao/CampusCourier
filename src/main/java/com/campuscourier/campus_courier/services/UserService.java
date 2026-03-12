package com.campuscourier.campus_courier.services;

import com.campuscourier.campus_courier.dto.LoginRequest;
import com.campuscourier.campus_courier.models.User;
import com.campuscourier.campus_courier.repositories.UserRepository;
import com.campuscourier.campus_courier.security.JwtUtil;
import com.campuscourier.campus_courier.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// Service layer is second layer in 3 tier architecture containing business logic
// @Service tells java that this class holds the service/brain logic of application
// this is the brain where logic of if else and conditions of the data is retrieved and processed
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // This is called as dependency injection, where class is injected in field constructor and initialized
    // Spring boot strongly forbids created a new object from class as we do with using new keyword such Product product = new Product
    // @Autowired annotation tells When you start up, please find the UserRepository and hand it to me, so I can use it
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(User user){
        // logic checks and legality checks
        // the logic to check and verify user will go here

        String plainPassword = user.getPassword();

        String hashPassword = passwordEncoder.encode(plainPassword);

        user.setPassword(hashPassword);

        // .save() saves the user object to database Postgres
        return userRepository.save(user);  // .save() is not initially defined in UserRepository. but it does not give error because
        // UserRepository extends JpaRepository. Spring data JPA automatically provides these fully functioning database commands
        // JPA stands for Java Persistence API, it significantly reduced boilerplate code required for database interaction
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String loginUser(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User with this email not found"
                ));

        // see if the BCrypt password matches the given password
        boolean isPasswordMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!isPasswordMatch){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid password"
            );
        }

        // login is successful if the checks are complete
        // return JWT later rather than plaintext

        String token = jwtUtil.generateToken(user.getEmail());
        return token;
    }
}
