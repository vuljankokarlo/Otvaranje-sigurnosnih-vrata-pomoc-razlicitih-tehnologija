package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

public class ObjectOpen {

    private String triggerTypeName;
    private String value;
    private String objectName;

    public ObjectOpen(String value, String objectName)
    {
        this.triggerTypeName = "App";
        this.value = value;
        this.objectName = objectName;
    }

    public String getTriggerTypeName() {
        return triggerTypeName;
    }

    public void setTriggerTypeName(String triggerTypeName) {
        this.triggerTypeName = triggerTypeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
