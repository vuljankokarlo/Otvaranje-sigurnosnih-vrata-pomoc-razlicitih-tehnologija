package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

import java.io.Serializable;

public class CrudUserDataClass implements Serializable {

    private int usrId;
    private String usrName;
    private String usrSurname;
    private String usrEmail;
    private int usrActivity;
    private int usrRolId;
    private boolean genPassword;

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getUsrSurname() {
        return usrSurname;
    }

    public void setUsrSurname(String usrSurname) {
        this.usrSurname = usrSurname;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public int getActivity() {
        return usrActivity;
    }

    public void setActivity(int activity) {
        usrActivity = activity;
    }

    public int getUsrRolId() {
        return usrRolId;
    }

    public void setUsrRolId(int usrRolId) {
        this.usrRolId = usrRolId;
    }

    public boolean isGeneratePassword() {
        return genPassword;
    }

    public void setGeneratePassword(boolean generatePassword) {
        this.genPassword = generatePassword;
    }

    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }
}
