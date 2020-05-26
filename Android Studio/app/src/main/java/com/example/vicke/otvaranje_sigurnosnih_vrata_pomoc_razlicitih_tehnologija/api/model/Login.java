package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

public class Login {

    private String UsrEmail;
    private String LoginPassword;

    public Login(String user, String password)
    {
        this.UsrEmail = user;
        this.LoginPassword = password;
    }
}
