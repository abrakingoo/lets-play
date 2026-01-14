package abu.lets_play.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abu.lets_play.Model.dto.UserSignIn;
import abu.lets_play.Model.dto.UserSignUp;
import abu.lets_play.Service.Userservice;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final Userservice userService;

    public AuthController(Userservice userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserSignUp entity) {
        return userService.signUp(entity);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSignIn entity) {
        return userService.signIn(entity);
    }
}
