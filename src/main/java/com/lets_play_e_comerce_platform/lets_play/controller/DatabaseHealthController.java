package com.lets_play_e_comerce_platform.lets_play.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseHealthController {
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @GetMapping("/db-status")
    public String checkDatabaseConnection() {
        try {
            String dbUser = System.getenv("DB_USERNAME");
            String dbPass = System.getenv("DB_PASSWORD");
            
            if (dbUser == null || dbPass == null) {
                return "Environment variables not loaded. DB_USERNAME=" + dbUser + ", DB_PASSWORD=" + (dbPass != null ? "[SET]" : "[NOT SET]");
            }
            
            mongoTemplate.getDb().runCommand(new org.bson.Document("ping", 1));
            return "✅ MongoDB connected successfully!";
        } catch (Exception e) {
            return "❌ MongoDB connection failed: " + e.getMessage();
        }
    }
}