package abu.lets_play.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import abu.lets_play.Model.Entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    
}
