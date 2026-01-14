package abu.lets_play.Service;

import abu.lets_play.Model.Entity.Product;
import abu.lets_play.Model.dto.NewProductRequest;
import abu.lets_play.Model.dto.ProductPatchRequest;
import abu.lets_play.Repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Product findById( String id) {
        return repo.findById(id).orElse(null);
    }

    public ResponseEntity<Map<String, Object>> addNewProduct(NewProductRequest request) {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        
        boolean saved = repo.save(product) != null;
        
        return ResponseEntity.status(saved ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("message", saved ? "Product added successfully" : "Error adding Product"));
    }

    public ResponseEntity<?> updateProduct(String id, ProductPatchRequest entity) {
        final String name = entity.getName();
        final String description = entity.getDescription();
        final Integer price = entity.getPrice();
        
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "id is required"));
        }
        if (name == null && description == null && price == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "At least one field is required"));
        }
        if (price != null && price < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Price must be greater than 0"));
        }

        Product existingProduct = repo.findById(id).orElse(null);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Product not found"));
        }
        
        if (name != null) existingProduct.setName(name);
        if (description != null) existingProduct.setDescription(description);
        if (price != null) existingProduct.setPrice(price);

        if (repo.save(existingProduct) == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error updating Product"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "message", "Product updated successfully",
            "product", Map.of(
                "id", id,
                "name", existingProduct.getName(),
                "description", existingProduct.getDescription(),
                "price", existingProduct.getPrice()
            )
        ));
    }

    public ResponseEntity<?> deleteProduct(String id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "id is required"));
        }
        repo.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Product deleted successfully"));
    }
}
