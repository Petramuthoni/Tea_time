package com.example.teatime.model;

public class Tea {

    private String mTeaName;
    private int mImageResourceId;

    public Tea(String teaName, int imageResourceId) {
        mTeaName = teaName;
        mImageResourceId = imageResourceId;
    }

    public String getTeaName() {
        return mTeaName;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

}