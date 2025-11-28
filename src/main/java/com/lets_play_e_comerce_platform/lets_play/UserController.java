package com.lets_play_e_comerce_platform.lets_play;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

        List<User> users = new ArrayList<User>();
        
        public UserController() {
            users.add(new User("John Doe", "john@example.com", "password", "user"));
            users.add(new User("Jane Doe", "jane@example.com", "password", "user"));
        }
        
        @GetMapping("/users")
        public List<User> getUser() {
            return users;
        }

    
}
