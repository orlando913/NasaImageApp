package com.example.nasaimageapp;

public class ImageInfo {
    private String date;
    private String imageUrl;



    public ImageInfo(String imageName, String imageUrl, String toString, String date) {
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
