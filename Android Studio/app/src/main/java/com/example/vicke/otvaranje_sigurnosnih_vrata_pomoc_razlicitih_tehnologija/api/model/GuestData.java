package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model;

public class GuestData {

    private String guestValidFrom;
    private String guestValidTo;
    private String phoneNumber;
    private int guestObjId;
    private boolean genPassword;

    public String getDateFrom() {
        return guestValidFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.guestValidFrom = dateFrom;
    }

    public String getDateTo() {
        return guestValidTo;
    }

    public void setDateTo(String dateTo) {
        this.guestValidTo = dateTo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getObjectId() {
        return guestObjId;
    }

    public void setObjectId(int objectId) {
        this.guestObjId = objectId;
    }

    public boolean isGenPassword() {
        return genPassword;
    }

    public void setGenPassword(boolean genPassword) {
        this.genPassword = genPassword;
    }
}
