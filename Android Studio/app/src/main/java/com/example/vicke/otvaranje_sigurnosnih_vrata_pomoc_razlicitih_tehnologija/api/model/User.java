package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

import java.io.Serializable;

public class User implements Serializable {

    private int Id;
    private String Email;
    private String FirstName;
    private String LastName;
    private String Token;
    private int Role;

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public int getId() {
        return Id;
    }

    public String getEmail() {
        return Email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getToken() {
        return Token;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setToken(String token) {
        Token = token;
    }
}
