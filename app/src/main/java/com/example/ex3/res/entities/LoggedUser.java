package com.example.ex3.res.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LoggedUser {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String token;
    private String server;

    public LoggedUser(String username) {
        this.username = username;
        this.server = "10.0.2.2:7290";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
