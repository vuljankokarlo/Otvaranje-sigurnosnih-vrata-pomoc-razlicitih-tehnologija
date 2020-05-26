package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

import java.io.Serializable;

public class AllUser implements Serializable {

    private int usrId;
    private String usrName;
    private String usrSurname;
    private String usrEmail;
    private int Activity;
    private int usrRolId;

    public AllUser(int usrId, String usrName, String usrSurname, String usrEmail, int activity, int usrRolId) {
        this.usrId = usrId;
        this.usrName = usrName;
        this.usrSurname = usrSurname;
        this.usrEmail = usrEmail;
        Activity = activity;
        this.usrRolId = usrRolId;
    }

    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

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
        return Activity;
    }

    public void setActivity(int activity) {
        Activity = activity;
    }

    public int getUsrRolId() {
        return usrRolId;
    }

    public void setUsrRolId(int usrRolId) {
        this.usrRolId = usrRolId;
    }

    @Override
    public String toString() {
        return usrName+" "+usrSurname;
    }
}
