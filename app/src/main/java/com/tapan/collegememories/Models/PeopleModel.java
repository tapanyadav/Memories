package com.tapan.collegememories.Models;

public class PeopleModel {
    private String peopleName;
    private String peopleImage;

    public PeopleModel() {
    }

    public PeopleModel(String peopleName, String peopleImage) {
        this.peopleName = peopleName;
        this.peopleImage = peopleImage;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeopleImage() {
        return peopleImage;
    }

    public void setPeopleImage(String peopleImage) {
        this.peopleImage = peopleImage;
    }
}
