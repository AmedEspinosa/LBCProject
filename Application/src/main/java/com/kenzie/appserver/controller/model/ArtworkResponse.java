package com.kenzie.appserver.controller.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtworkResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("artistName")
    private String artistName;

    @JsonProperty("title")
    private String title;

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

    @JsonProperty("price")
    private int price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean sold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public boolean forSale() {
        return isForSale;
    }

    public void setForSale(boolean forSale) {
        isForSale = forSale;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
