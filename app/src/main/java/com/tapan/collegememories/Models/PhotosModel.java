package com.tapan.collegememories.Models;

public class PhotosModel {

    String photoImage;
    String photoCaption;
    String id;

    public PhotosModel() {
    }

    public PhotosModel(String photoImage, String photoCaption,String id) {
        this.photoImage = photoImage;
        this.photoCaption = photoCaption;
        this.id = id;
    }

    public String getPhotoImage() {
        return photoImage;
    }

    public String getPhotoCaption() {
        return photoCaption;
    }

    public void setPhotoImage(String photoImage) {
        this.photoImage = photoImage;
    }

    public void setPhotoCaption(String photoCaption) {
        this.photoCaption = photoCaption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
