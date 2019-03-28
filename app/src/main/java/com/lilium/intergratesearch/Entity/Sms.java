package com.lilium.intergratesearch.Entity;

public class Sms {
    private String smsName;
    private String smsNumber;
    private String smsBody;

    public Sms(String smsName, String smsNumber, String smsBody) {
        this.smsName = smsName;
        this.smsNumber = smsNumber;
        this.smsBody = smsBody;
    }

    public String getSmsName() {
        return smsName;
    }

    public void setSmsName(String smsName) {
        this.smsName = smsName;
    }

    public String getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
    }

    public String getSmsBody() {
        return smsBody;
    }

    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }
}
