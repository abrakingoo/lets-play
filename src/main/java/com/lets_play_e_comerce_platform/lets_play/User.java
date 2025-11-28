package com.lets_play_e_comerce_platform.lets_play;

public class User {
    String id;
    String name;
    String email;
    String password;
    String role;

    public User(String id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
