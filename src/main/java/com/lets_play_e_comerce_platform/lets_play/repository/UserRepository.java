package com.lets_play_e_comerce_platform.lets_play.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lets_play_e_comerce_platform.lets_play.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByname(String name);
    boolean existsByname(String name);
    User findFirstByEmail(String email);
    boolean existsByEmail(String email);
}
