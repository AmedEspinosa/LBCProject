package com.kenzie.appserver.service.model;

public class Artwork {

    private final String id;
    private final String datePosted; //MMDDYYYY
    private final String artistName;
    private final String title;
    private final String dateCreated; //MMDDYYYY
    private final int height;
    private final int width;
    private final boolean sold;
    private final boolean forSale;
    private final int price;

    public Artwork(String id, String datePosted, String artistName, String title, String dateCreated, int height,
                   int width, boolean isSold, boolean isForSale, int price) {
        this.id = id;
        this.datePosted = datePosted;
        this.artistName = artistName;
        this.title = title;
        this.dateCreated = dateCreated;
        this.height = height;
        this.width = width;
        this.sold = isSold;
        this.forSale = isForSale;
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
        return sold;
    }

    public boolean getIsForSale() {
        return forSale;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "id='" + id + '\'' +
                ", datePosted='" + datePosted + '\'' +
                ", artistName='" + artistName + '\'' +
                ", title='" + title + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", sold=" + sold +
                ", forSale=" + forSale +
                ", price=" + price +
                '}';
    }
}
