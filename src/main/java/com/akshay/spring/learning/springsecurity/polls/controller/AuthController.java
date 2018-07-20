package com.akshay.spring.learning.springsecurity.polls.controller;

import com.akshay.spring.learning.springsecurity.polls.dao.UserDao;
import com.akshay.spring.learning.springsecurity.polls.model.User;
import com.akshay.spring.learning.springsecurity.polls.payload.ApiResponse;
import com.akshay.spring.learning.springsecurity.polls.payload.JwtAuthenticationResponse;
import com.akshay.spring.learning.springsecurity.polls.payload.LoginRequest;
import com.akshay.spring.learning.springsecurity.polls.payload.SignUpRequest;
import com.akshay.spring.learning.springsecurity.polls.respository.UserRepository;
import com.akshay.spring.learning.springsecurity.polls.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserDao userDao;

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        final String token = jwtTokenProvider.generateToken(authenticate);

        return new JwtAuthenticationResponse(token);
    }

    @PutMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userDao.existsByUsername(signUpRequest)){
            return new ResponseEntity<>(
                    new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST
            );
        }

        if(userDao.existsByEmail(signUpRequest)) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        final User user = userDao.createUserAccount(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }

}
