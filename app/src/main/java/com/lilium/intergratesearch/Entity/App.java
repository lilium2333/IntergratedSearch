package com.lilium.intergratesearch.Entity;

import android.graphics.Bitmap;

public class App {
    public String appName;
    public Bitmap appImage;
    public String appPackgeName;

    public App(String appName, Bitmap appImage, String appPackgeName) {
        this.appName = appName;
        this.appImage = appImage;
        this.appPackgeName = appPackgeName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Bitmap getAppImage() {
        return appImage;
    }

    public void setAppImage(Bitmap appImage) {
        this.appImage = appImage;
    }

    public String getAppPackgeName() {
        return appPackgeName;
    }

    public void setAppPackgeName(String appPackgeName) {
        this.appPackgeName = appPackgeName;
    }
}
