package com.kenzie.appserver.controller.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ArtworkUpdateRequest {

    @NotEmpty
    @JsonProperty("id")
    private String id;

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @JsonProperty("datePosted")
    private String datePosted;

    @JsonProperty("artistName")
    private String artistName;

    @JsonProperty("title")
    private String title;

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @JsonProperty("dateCreated")
    private String dateCreated;

    @Min(0)
    @Max(240) //inches (240" == 20ft)
    @JsonProperty("height")
    private int height;

    @Min(0)
    @Max(240) //inches (240" == 20ft)
    @JsonProperty("width")
    private int width;

    @JsonProperty("isSold")
    private boolean isSold;

    @JsonProperty("isForSale")
    private boolean isForSale;

    @Min(0)
    @JsonProperty("price")
    private int price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean getIsSold() {
        return isSold;
    }

    public void setIsSold(boolean isSold) {
        this.isSold = isSold;
    }

    public boolean getIsForSale() {
        return isForSale;
    }

    public void setIsForSale(boolean isForSale) {
        this.isForSale = isForSale;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
