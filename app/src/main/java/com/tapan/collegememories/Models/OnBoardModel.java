package com.tapan.collegememories.Models;

public class OnBoardModel {

    private String Title,Description;
    private int ScreenImg;

    public OnBoardModel(){

    }

    public OnBoardModel(String title, String description, int screenImg) {
        Title = title;
        Description = description;
        ScreenImg = screenImg;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title) {

        Title = title;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description) {

        Description = description;
    }

    public int getScreenImg() {

        return ScreenImg;
    }

    public void setScreenImg(int screenImg) {

        ScreenImg = screenImg;
    }
}
