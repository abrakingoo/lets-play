package abu.lets_play.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import abu.lets_play.Model.Entity.User;

public interface UserRepository extends MongoRepository<User, String>{
    User findByEmail(String email);
} 