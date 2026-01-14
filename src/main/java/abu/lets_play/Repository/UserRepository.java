package abu.lets_play.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import abu.lets_play.Model.Entity.User;

public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByEmail(String email);
} 