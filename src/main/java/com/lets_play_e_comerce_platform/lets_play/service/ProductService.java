package com.lets_play_e_comerce_platform.lets_play.service;

import com.lets_play_e_comerce_platform.lets_play.model.Product;
import com.lets_play_e_comerce_platform.lets_play.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repo;
    public ProductService(ProductRepository repo) { this.repo = repo; }

    public List<Product> all() { return repo.findAll(); }
    public Optional<Product> findById(String id) { return repo.findById(id); }
    public Product create(Product p) { return repo.save(p); }
    public Product update(Product p) { return repo.save(p); } // save acts as insert/update
    public boolean delete(String id) { 
        try {
            if (repo.existsById(id)) {
                repo.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    public List<Product> findByUser(String userId) { return repo.findByUserId(userId); }
}
