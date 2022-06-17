package com.example.ex3.res.entities;

public class NewUser {
    private String username;
    private String nickname;
    private String password;

    public NewUser(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
