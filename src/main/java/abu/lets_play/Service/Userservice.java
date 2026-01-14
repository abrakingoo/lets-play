package abu.lets_play.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import abu.lets_play.Model.Entity.User;
import abu.lets_play.Model.Enums.Role;
import abu.lets_play.Model.dto.UserSignUp;
import abu.lets_play.Repository.UserRepository;

@Service
public class Userservice {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Userservice(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> signUp(UserSignUp userSignUp) {
        try {
            if (userRepository.findByEmail(userSignUp.getEmail()).isPresent()) {
                throw new IllegalStateException("Email already exists");
            }
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setName(userSignUp.getName());
            user.setEmail(userSignUp.getEmail());
            user.setPassword(passwordEncoder.encode(userSignUp.getPassword()));
            user.setRole(Role.USER);
            User savedUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "User created successfully",
                "user", savedUser
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
