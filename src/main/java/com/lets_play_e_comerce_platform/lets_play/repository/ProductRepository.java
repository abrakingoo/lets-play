package com.lets_play_e_comerce_platform.lets_play.repository;

import com.lets_play_e_comerce_platform.lets_play.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByUserId(String userId);
}
