package com.example.banksystem.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class BankOfficerPrototype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin")
    private Admin admin;

    public BankOfficerPrototype() {
    }

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

    public BankOfficerPrototype(String username, String password, Admin admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }
}
