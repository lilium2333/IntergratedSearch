package com.lilium.intergratesearch.Entity;

public class Contacts {
    private String contactsName;
    private String contactsNumber;

    public Contacts(String contactsName, String contactsNumber) {
        this.contactsName = contactsName;
        this.contactsNumber = contactsNumber;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsNumber() {
        return contactsNumber;
    }

    public void setContactsNumber(String contactsNumber) {
        this.contactsNumber = contactsNumber;
    }
}
