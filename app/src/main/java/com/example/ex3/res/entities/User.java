package com.example.ex3.res.entities;

public class User {
    private String username;
    private String password;
    private String firebaseToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public User(String username, String password, String firebaseToken) {
        this.username = username;
        this.password = password;
        this.firebaseToken = firebaseToken;

    }
}
