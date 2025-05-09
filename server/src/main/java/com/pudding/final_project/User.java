package com.pudding.final_project;

import com.pudding.final_project.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
public class User extends BaseEntity {
    private String username;
    private String password;

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
} 