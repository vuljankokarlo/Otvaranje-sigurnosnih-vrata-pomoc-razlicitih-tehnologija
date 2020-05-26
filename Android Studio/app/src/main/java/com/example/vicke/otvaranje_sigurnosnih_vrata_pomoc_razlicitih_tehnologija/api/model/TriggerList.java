package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

import java.io.Serializable;

public class TriggerList implements Serializable {

    private int trgUsrId;
    private String trgValue;
    private int trgTrtId;
    private int trgActivity;

    public int getTrgUsrId() {
        return trgUsrId;
    }

    public void setTrgUsrId(int trgUsrId) {
        this.trgUsrId = trgUsrId;
    }

    public String getTrgValue() {
        return trgValue;
    }

    public void setTrgValue(String trgValue) {
        this.trgValue = trgValue;
    }

    public int getTrgActivity() {
        return trgActivity;
    }

    public void setTrgActivity(int trgActivity) {
        this.trgActivity = trgActivity;
    }

    public int getTrgTrtId() {
        return trgTrtId;
    }

    public void setTrgTrtId(int trgTrtId) {
        this.trgTrtId = trgTrtId;
    }

    @Override
    public String toString()
    {
        return  "Trigger value: " + trgValue + "\n" + "Trigger activity: " + trgActivity;
    }
}
