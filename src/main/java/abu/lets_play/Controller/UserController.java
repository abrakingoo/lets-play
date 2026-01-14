package abu.lets_play.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abu.lets_play.Model.Entity.User;
import abu.lets_play.Service.Userservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Userservice userService;

    public UserController(Userservice userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getMethodName() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable String id) {
        return userService.findById(id);
    }
    
    
}
