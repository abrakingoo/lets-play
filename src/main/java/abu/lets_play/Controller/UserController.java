package abu.lets_play.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abu.lets_play.Model.Entity.User;
import abu.lets_play.Model.dto.UserSignIn;
import abu.lets_play.Model.dto.UserSignUp;
import abu.lets_play.Service.Userservice;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserSignUp entity) {
        return userService.signUp(entity);
    }

    @GetMapping
    public List<User> getMethodName() {
        return userService.findAll();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSignIn entity) {
        return userService.signIn(entity); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable String id) {
        return userService.findById(id);
    }
    
    
}
