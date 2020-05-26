package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

public class CrudProfileListItemData {

    private String accessFrom;
    private String accessTo;
    private String profileName;
    private String objectName;

    public CrudProfileListItemData(String accessFrom, String accessTo, String profileName, String objectName) {
        this.accessFrom = accessFrom;
        this.accessTo = accessTo;
        this.profileName = profileName;
        this.objectName = objectName;
    }

    public String getAccessFrom() {
        return accessFrom;
    }

    public void setAccessFrom(String accessFrom) {
        this.accessFrom = accessFrom;
    }

    public String getAccessTo() {
        return accessTo;
    }

    public void setAccessTo(String accessTo) {
        this.accessTo = accessTo;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public String toString() {
        return "Access from: " + accessFrom + "\n"
                + "Access to: " + accessTo + "\n"
                + "Profile: " + profileName + "\n"
                + "Object: " + objectName;
    }
}
