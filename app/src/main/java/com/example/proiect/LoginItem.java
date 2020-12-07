package com.example.proiect;

public class LoginItem {
    private String mLoginName;
    private int mShareImage;

    public LoginItem(String mLoginName, int mShareImage) {
        this.mLoginName = mLoginName;
        this.mShareImage = mShareImage;
    }

    public String getmLoginName() {
        return mLoginName;
    }

    public void setmLoginName(String mLoginName) {
        this.mLoginName = mLoginName;
    }

    public int getmShareImage() {
        return mShareImage;
    }

    public void setmShareImage(int mShareImage) {
        this.mShareImage = mShareImage;
    }
}


