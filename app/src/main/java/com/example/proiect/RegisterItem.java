package com.example.proiect;

public class RegisterItem {
    private String mRegisterName;
    private int mShareRegImage;

    public RegisterItem(String mRegisterName, int mShareRegImage) {
        this.mRegisterName = mRegisterName;
        this.mShareRegImage = mShareRegImage;

    }

    public String getmRegisterName() { return mRegisterName; }

    public void setmRegisterName(String mRegisterName) {
        this.mRegisterName = mRegisterName;
    }

    public int getmShareRegImage() {
        return mShareRegImage;
    }

    public void setmShareRegImage(int mShareRegImage) {
        this.mShareRegImage = mShareRegImage;
    }
}