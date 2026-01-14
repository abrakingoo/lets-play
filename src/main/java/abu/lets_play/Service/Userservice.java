package abu.lets_play.Service;

import org.springframework.stereotype.Service;

import abu.lets_play.Model.Entity.User;
import abu.lets_play.Model.dto.UserSignUp;
import abu.lets_play.Repository.UserRepository;

@Service
public class Userservice {
    private final UserRepository userRepository;

    public Userservice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserSignUp userSignUp) {
        if (userRepository.findByEmail(userSignUp.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setName(userSignUp.getName());
        user.setEmail(userSignUp.getEmail());
        user.setPassword(userSignUp.getPassword());
        return userRepository.save(user);
    }
    
}
