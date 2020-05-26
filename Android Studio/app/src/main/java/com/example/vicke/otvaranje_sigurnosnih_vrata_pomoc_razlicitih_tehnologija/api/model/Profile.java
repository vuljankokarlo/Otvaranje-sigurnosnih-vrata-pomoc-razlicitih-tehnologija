package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

import java.io.Serializable;

public class Profile implements Serializable {

    private int proId;
    private String proName;
    private int proActivity;

    public Profile()
    {

    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getProActivity() {
        return proActivity;
    }

    public void setProActivity(int proActivity) {
        this.proActivity = proActivity;
    }

    @Override
    public String toString() {
        return proName;
    }
}
