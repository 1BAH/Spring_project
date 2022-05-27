package com.example.banksystem.models;

import javax.persistence.*;

/**
 * BankOfficerPrototype entity class with <b>id</b>, <b>username</b>, <b>password</b> and <b>admin</b> properties.
 */
@Entity
public class BankOfficerPrototype {

    /**
     * BankOfficerPrototype's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * BankOfficerPrototype's username
     */
    private String username;

    /**
     * BankOfficerPrototype's password
     */
    private String password;

    /**
     * BankOfficerPrototype's admin
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin")
    private Admin admin;

    public BankOfficerPrototype() {
    }

    /**
     * Get username of bank officer prototype
     * @return username of bank officer prototype
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username of bank officer prototype
     * @param username - name of bank officer prototype
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password for account of bank officer prototype
     * @return password for account of bank officer prototype
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password for account of bank officer prototype
     * @param password for account of bank officer prototype
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Constructor of BankOfficerPrototype objects of three parameters
     * @param username - name of bank officer prototype
     * @param password - password for account of bank officer prototype
     * @param admin who works with this bank officer prototype
     */
    public BankOfficerPrototype(String username, String password, Admin admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }
}
