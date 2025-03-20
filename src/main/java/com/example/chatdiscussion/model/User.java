package com.example.chatdiscussion.model;

public class User {
    private int id;
    private String username;
    private String statuts;

    public User(int id, String username, String statuts) {
        this.id = id;
        this.username = username;
        this.statuts = statuts;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getStatuts() {
        return statuts;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStatuts(String statuts) {
        this.statuts = statuts;
    }
}