package com.abhishek.sweet_kata_shop.controller;

import com.abhishek.sweet_kata_shop.dto.UserLoginRequest;
import com.abhishek.sweet_kata_shop.dto.UserRegisterRequest;
import com.abhishek.sweet_kata_shop.dto.UserResponse;
import com.abhishek.sweet_kata_shop.model.User;
import com.abhishek.sweet_kata_shop.repository.UserRepository;
import com.abhishek.sweet_kata_shop.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtils jwt;

    public AuthController(UserRepository users, PasswordEncoder encoder,
                          AuthenticationManager authManager, JwtUtils jwt) {
        this.users = users;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest req) {
        if (users.existsByUsernameIgnoreCase(req.username) || users.existsByEmailIgnoreCase(req.email)) {
            return ResponseEntity.badRequest().build();
        }
        var u = new User();
        u.setUsername(req.username);
        u.setEmail(req.email);
        u.setPassword(encoder.encode(req.password));
        u.setRole("USER");
        u = users.save(u);
        var resp = new UserResponse();
        resp.id = u.getId();
        resp.username = u.getUsername();
        resp.email = u.getEmail();
        resp.role = u.getRole();
        resp.enabled = u.getEnabled();
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest req) {
        var token = switch (req.usernameOrEmail) {
            default -> {
                authManager.authenticate(new UsernamePasswordAuthenticationToken(req.usernameOrEmail, req.password));
                yield jwt.generateToken(req.usernameOrEmail);
            }
        };
        return ResponseEntity.ok(java.util.Map.of("token", token));
    }
}