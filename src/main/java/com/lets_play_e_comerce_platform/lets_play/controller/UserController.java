package com.lets_play_e_comerce_platform.lets_play.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lets_play_e_comerce_platform.lets_play.model.User;
import com.lets_play_e_comerce_platform.lets_play.service.UserService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {

        List<User> users = new ArrayList<User>();
        private final UserService service;
        
        public UserController(UserService service ) {
            this.service = service;
        }
        
        @GetMapping("/users")
        public List<User> getUser() {
            return service.all();
        }

        @PostMapping("users")
        public ResponseEntity<?>  postMethodName(@RequestBody User entity) {
            if (entity == null) {
                return ResponseEntity.badRequest().body("Invalid input: 'entity' cannot be null.");
            }

            User user = service.save(entity);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user.");
            }
            return ResponseEntity.ok("User added successfully");
        }
        

    
}
