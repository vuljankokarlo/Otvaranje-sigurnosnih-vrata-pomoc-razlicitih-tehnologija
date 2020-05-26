package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

import java.io.Serializable;

public class EventLogData implements Serializable {

    private int eventLogId;
    private String date;
    private String triggerValue;
    private String userName;
    private String userSurname;
    private String triggerName;
    private String objectName;
    private String eventStatusName;

    public int getEventLogId() {
        return eventLogId;
    }

    public void setEventLogId(int eventLogId) {
        this.eventLogId = eventLogId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTriggerValue() {
        return triggerValue;
    }

    public void setTriggerValue(String triggerValue) {
        this.triggerValue = triggerValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getEventStatusName() {
        return eventStatusName;
    }

    public void setEventStatusName(String eventStatusName) {
        this.eventStatusName = eventStatusName;
    }

    @Override
    public String toString() {
        String str = "";
        if (userName != null)
        {
            str += "User: " + userName + " ";
        }
        if (userSurname != null)
        {
            str += userSurname + "\n";
        }
        if (triggerName != null)
        {
            str += triggerName + " -> ";
        }
        if (triggerValue != null)
        {
            str += triggerValue + "\n";
        }
        if (objectName != null)
        {
            str += "Object name: " + objectName + "\n";
        }
        if (eventStatusName != null)
        {
            str += "Status: " + eventStatusName + "\n";
        }
        if (date != null)
        {
            str+= "Date: " + date;
        }
        return str;
    }
}
