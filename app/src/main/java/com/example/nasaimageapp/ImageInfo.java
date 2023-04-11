package com.example.nasaimageapp;

public class ImageInfo {
    private String name;
    private String date;
    private String explanation;
    private String imagePath;



    public ImageInfo() {
        this.name = name;
        this.date = date;
        this.explanation = explanation;
        this.imagePath = imagePath;
    }

    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

