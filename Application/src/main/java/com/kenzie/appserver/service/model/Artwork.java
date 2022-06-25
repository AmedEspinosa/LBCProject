package com.kenzie.appserver.service.model;

public class Artwork {

    private final String id;
    private final String datePosted; //MMDDYYYY
    private final String artistName;
    private final String title;
    private final String dateCreated; //MMDDYYYY
    private final int height;
    private final int width;
    private final boolean isSold;
    private final boolean isForSale;
    private final Double price;

    public Artwork(String id, String datePosted, String artistName, String title, String dateCreated, int height,
                   int width, boolean isSold, boolean isForSale, Double price) {
        this.id = id;
        this.datePosted = datePosted;
        this.artistName = artistName;
        this.title = title;
        this.dateCreated = dateCreated;
        this.height = height;
        this.width = width;
        this.isSold = isSold;
        this.isForSale = isForSale;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTitle() {
        return title;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean getIsSold() {
        return isSold;
    }

    public boolean getIsForSale() {
        return isForSale;
    }

    public Double getPrice() {
        return price;
    }
}