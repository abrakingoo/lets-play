package abu.lets_play.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import abu.lets_play.Model.Entity.Product;
import abu.lets_play.Model.dto.NewProductRequest;
import abu.lets_play.Model.dto.ProductPatchRequest;
import abu.lets_play.Service.ProductService;
import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> addNewProduct(@Valid @RequestBody NewProductRequest request) {
        Product product = new Product();
        Boolean saved = false;
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        if (productService.create(product) != null){
            saved = true;
        };
        
        Map<String, Object> response = Map.of(
            "message", saved ? "Product added successfully" : "Error adding Product",
            "product", product
        );

        return ResponseEntity.status(saved ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody ProductPatchRequest entity) {
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

        Product existingProduct = productService.findById(id).orElse(null);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Product not found"));
        }
        
        if (name != null) {
            existingProduct.setName(name);
        }
        if (description != null) {
            existingProduct.setDescription(description);
        }
        if (price != null) {
            existingProduct.setPrice(price);
        }

        if (productService.save(existingProduct) == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error updating Product"));
        };

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "message", "Product updated successfully",
            "product", Map.of(
                "id", id,
                "name", name != null ? name : existingProduct.getName(),
                "description", description != null ? description : existingProduct.getDescription(),
                "price", price != null ? price : existingProduct.getPrice()
            )
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "id is required"));
        }
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
            "message", "Product deleted successfully"
        ));
    }
}
