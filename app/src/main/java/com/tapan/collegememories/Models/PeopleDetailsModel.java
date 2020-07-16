package com.tapan.collegememories.Models;

public class PeopleDetailsModel {
    private String peopleImage;
    private String peopleName;
    private String peopleNickname;
    private String peopleBio;
    private String peopleDateOfBirth;
    private String peopleDateOne;
    private String peopleDateTwo;
    private String instagramLink;
    private String facebookLink;
    private String snapchatLink;
    private String linkedinLink;
    private String githubLink;
    private String peopleCrush;

    public PeopleDetailsModel(){
    }

    public PeopleDetailsModel(String peopleImage, String peopleName, String peopleNickname, String peopleBio, String peopleDateOfBirth, String peopleDateOne, String peopleDateTwo, String instagramLink, String facebookLink, String snapchatLink, String linkedinLink, String githubLink,String peopleCrush) {
        this.peopleImage = peopleImage;
        this.peopleName = peopleName;
        this.peopleNickname = peopleNickname;
        this.peopleBio = peopleBio;
        this.peopleDateOfBirth = peopleDateOfBirth;
        this.peopleDateOne = peopleDateOne;
        this.peopleDateTwo = peopleDateTwo;
        this.instagramLink = instagramLink;
        this.facebookLink = facebookLink;
        this.snapchatLink = snapchatLink;
        this.linkedinLink = linkedinLink;
        this.githubLink = githubLink;
        this.peopleCrush = peopleCrush;
    }



    public String getPeopleImage() {
        return peopleImage;
    }

    public void setPeopleImage(String peopleImage) {
        this.peopleImage = peopleImage;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeopleNickname() {
        return peopleNickname;
    }

    public void setPeopleNickname(String peopleNickname) {
        this.peopleNickname = peopleNickname;
    }

    public String getPeopleBio() {
        return peopleBio;
    }

    public void setPeopleBio(String peopleBio) {
        this.peopleBio = peopleBio;
    }

    public String getPeopleDateOfBirth() {
        return peopleDateOfBirth;
    }

    public void setPeopleDateOfBirth(String peopleDateOfBirth) {
        this.peopleDateOfBirth = peopleDateOfBirth;
    }

    public String getPeopleDateOne() {
        return peopleDateOne;
    }

    public void setPeopleDateOne(String peopleDateOne) {
        this.peopleDateOne = peopleDateOne;
    }

    public String getPeopleDateTwo() {
        return peopleDateTwo;
    }

    public void setPeopleDateTwo(String peopleDateTwo) {
        this.peopleDateTwo = peopleDateTwo;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getSnapchatLink() {
        return snapchatLink;
    }

    public void setSnapchatLink(String snapchatLink) {
        this.snapchatLink = snapchatLink;
    }

    public String getLinkedinLink() {
        return linkedinLink;
    }

    public void setLinkedinLink(String linkedinLink) {
        this.linkedinLink = linkedinLink;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public String getPeopleCrush() {
        return peopleCrush;
    }

    public void setPeopleCrush(String peopleCrush) {
        this.peopleCrush = peopleCrush;
    }
}
