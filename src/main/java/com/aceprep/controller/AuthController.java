package com.aceprep.controller;

import com.aceprep.DTO.AuthResponse;
import com.aceprep.DTO.LoginRequest;
import com.aceprep.DTO.RegisterRequest;
import com.aceprep.entity.User;
import com.aceprep.repository.UserRepository;
import com.aceprep.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();
        userRepository.save(newUser);
        return AuthResponse.builder()
                .email(newUser.getEmail())
                .message("Register successful")
                .token("User Created Succesfully")
                .build();
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("User Not Found"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .build();
    }
}
