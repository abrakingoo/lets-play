package com.lets_play_e_comerce_platform.lets_play.model;

import java.util.UUID;

public class User {
    String id;
    String name;
    String email;
    String password;
    String role;

    public User(String name, String email, String password, String role) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}
