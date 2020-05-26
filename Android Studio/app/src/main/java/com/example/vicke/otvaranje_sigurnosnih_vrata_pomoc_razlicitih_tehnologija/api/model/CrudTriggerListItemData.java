package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

public class CrudTriggerListItemData {

    private String triggerName;
    private String triggerValue;
    private int triggerActivity;

    public CrudTriggerListItemData(String triggerName, String triggerValue, int triggerActivity) {
        this.triggerName = triggerName;
        this.triggerValue = triggerValue;
        this.triggerActivity = triggerActivity;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerValue() {
        return triggerValue;
    }

    public void setTriggerValue(String triggerValue) {
        this.triggerValue = triggerValue;
    }

    public int getTriggerActivity() {
        return triggerActivity;
    }

    public void setTriggerActivity(int triggerActivity) {
        this.triggerActivity = triggerActivity;
    }

    @Override
    public String toString() {
        return "Trigger name: " + triggerName + "\n"
             + "Trigger value: " + triggerValue + "\n"
             + "Trigger activity: " + triggerActivity;
    }
}
