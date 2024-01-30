package com.example.sharelibrary;

public class PackageModel {
    private int imageId;
    private int titleId;

    public PackageModel(int titleId2, int imageId2) {
        this.titleId = titleId2;
        this.imageId = imageId2;
    }

    public int getTitleId() {
        return this.titleId;
    }

    public void setTitleId(int titleId2) {
        this.titleId = titleId2;
    }

    public int getImageId() {
        return this.imageId;
    }

    public void setImageId(int imageId2) {
        this.imageId = imageId2;
    }
}
