package abu.lets_play.Service;

import abu.lets_play.Model.Entity.Product;
import abu.lets_play.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Product create(Product p) {
        return repo.save(p);
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Optional<Product> findById(String id) {
        return repo.findById(id);
    }

    public Product save(Product p) {
        return repo.save(p);
    }

    public void deleteById(String id) {
        repo.deleteById(id);
    }
}
