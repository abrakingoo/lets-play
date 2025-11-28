package com.lets_play_e_comerce_platform.lets_play.controller;

import com.lets_play_e_comerce_platform.lets_play.model.Product;
import com.lets_play_e_comerce_platform.lets_play.service.ProductService;

import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class ProductController {
    List<Product> products = new ArrayList<Product>();
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        products = service.all();
        Map<String, Object> response = new HashMap<>();
        response.put("total", products.size());
        response.put("products", products);

        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Product newProduct) {
        Product addedProduct = new Product(
            newProduct.getName(),
            newProduct.getDescription(),
            newProduct.getPrice(),
            newProduct.getUserId()
        );
        Product product = service.create(addedProduct);
        Map<String, Object> response = new HashMap<>();

        if (product == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        response.put("message", "Product added successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> editProduct(@PathVariable("id") String productId , @RequestBody Map<String, Object> updates) {
        Product product = service.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Product not found: No product exists with ID '" + productId + "'. Please check the product ID and try again.");
        }
        if (updates.containsKey("user-id")){
            if (!updates.get("user-id").equals(product.getUserId())) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Access denied: You can only edit products that you own.");
            }
        }

        if (updates.containsKey("name")) {
            String name = updates.get("name").toString();
            if (name.isEmpty() || name.isBlank()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Invalid input: 'name' cannot be empty.");
            }
            product.setName(name);
        }

        if (updates.containsKey("description")) {
            String description = updates.get("description").toString();
            if (description.isEmpty() || description.isBlank()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Invalid input: 'description' cannot be empty.");
            }
            product.setDescription(description);
        }

        if (updates.containsKey("price")) {
            String priceValue = updates.get("price").toString();
            if (priceValue.isEmpty() || priceValue.isBlank()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Invalid input: 'price' cannot be empty.");
            }

            double price = Double.parseDouble(priceValue);

            if (price < 0) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Invalid input: 'price' cannot be negative.");
            }

            product.setPrice(price);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(product);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String productId, @RequestBody Map<String, Object> updates) {
        Product product = null;
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Product not found: No product exists with ID '" + productId + "'. Please check the product ID and try again.");
        }
        if (updates.containsKey("user-id")){
            if (!updates.get("user-id").equals(product.getUserId())) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Access denied: You can only delete products that you own.");
            }
        }
        if (products.remove(product)) {
            return ResponseEntity.ok("Product deleted successfully.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete product: An error occurred while deleting the product with ID '" + productId + "'. Please try again later.");
        }
    }
}
