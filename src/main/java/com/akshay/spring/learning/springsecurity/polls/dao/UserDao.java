package com.akshay.spring.learning.springsecurity.polls.dao;

import com.akshay.spring.learning.springsecurity.polls.controller.AuthController;
import com.akshay.spring.learning.springsecurity.polls.exception.AppException;
import com.akshay.spring.learning.springsecurity.polls.model.Role;
import com.akshay.spring.learning.springsecurity.polls.model.RoleName;
import com.akshay.spring.learning.springsecurity.polls.model.User;
import com.akshay.spring.learning.springsecurity.polls.payload.SignUpRequest;
import com.akshay.spring.learning.springsecurity.polls.respository.RoleRepository;
import com.akshay.spring.learning.springsecurity.polls.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Collections;

public class UserDao {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    public User createUserAccount(SignUpRequest signUpRequest){
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);

    }

    public Boolean existsByUsername(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userRepository.existsByUsername(signUpRequest.getUsername());
    }

    public Boolean existsByEmail(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userRepository.existsByEmail(signUpRequest.getEmail());
    }
}
