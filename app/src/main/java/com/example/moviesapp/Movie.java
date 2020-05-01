package com.example.moviesapp;

public class Movie {

    private String originalTitle;
    private String avatar;
    private String sumary;
    private String title;
    private String voreAvrg;
    private String releaseData;
    private Boolean isFavourite = false;

    public Movie(String originalTitle, String avatar, String sumary, String title, String voreAvrg, String releaseData) {
        this.originalTitle = originalTitle;
        this.avatar = avatar;
        this.sumary = sumary;
        this.title = title;
        this.voreAvrg = voreAvrg;
        this.releaseData = releaseData;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoreAvrg() {
        return voreAvrg;
    }

    public void setVoreAvrg(String voreAvrg) {
        this.voreAvrg = voreAvrg;
    }

    public String getReleaseData() {
        return releaseData;
    }

    public void setReleaseData(String releaseData) {
        this.releaseData = releaseData;
    }
}
