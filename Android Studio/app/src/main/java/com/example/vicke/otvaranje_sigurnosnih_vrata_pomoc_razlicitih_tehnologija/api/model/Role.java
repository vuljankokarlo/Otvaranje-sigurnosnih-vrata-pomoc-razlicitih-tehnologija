package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

import java.io.Serializable;

public class Role implements Serializable {

    private int rolId;
    private String rolName;
    private String rolCompany;

    public Role(int rolId, String rolName, String rolCompany) {
        this.rolId = rolId;
        this.rolName = rolName;
        this.rolCompany = rolCompany;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public String getRolCompany() {
        return rolCompany;
    }

    public void setRolCompany(String rolCompany) {
        this.rolCompany = rolCompany;
    }

    @Override
    public String toString() {
        return rolName;
    }
}
