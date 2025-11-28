package com.lets_play_e_comerce_platform.lets_play.controller;

import com.lets_play_e_comerce_platform.lets_play.model.User;
import com.lets_play_e_comerce_platform.lets_play.repository.UserRepository;
import com.lets_play_e_comerce_platform.lets_play.security.JwtUtil;
import com.lets_play_e_comerce_platform.lets_play.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepo,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authManager,
                          JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String,String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");

        if (userRepo.existsByname(username)) {
            return ResponseEntity.badRequest().body("username taken");
        }
        if (userRepo.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("email taken");
        }

        User user = new User(username, email, passwordEncoder.encode(password), "ROLE_USER");
        userRepo.save(user);
        return ResponseEntity.ok("registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        try {
            String email = body.get("email");
            String password = body.get("password");

            // authenticate credentials
            authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            // load principal and create token
            UserPrincipal principal = new UserPrincipal(userRepo.findFirstByEmail(email));
            String token = jwtUtil.generateToken(principal);

            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }
}
