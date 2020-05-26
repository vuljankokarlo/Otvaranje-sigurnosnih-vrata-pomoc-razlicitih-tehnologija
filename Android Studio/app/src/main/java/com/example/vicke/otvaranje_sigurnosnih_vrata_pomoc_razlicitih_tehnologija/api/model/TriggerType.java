package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

import java.io.Serializable;

public class TriggerType implements Serializable {

    private int trtId;
    private String trtName;

    public TriggerType(int triggerTypeId, String triggerName) {
        this.trtId = triggerTypeId;
        this.trtName = triggerName;
    }

    public int getTriggerTypeId() {
        return trtId;
    }

    public void setTriggerTypeId(int triggerTypeId) {
        this.trtId = triggerTypeId;
    }

    public String getTriggerName() {
        return trtName;
    }

    public void setTriggerName(String triggerName) {
        this.trtName = triggerName;
    }

    @Override
    public String toString() {
        return trtName;
    }
}
