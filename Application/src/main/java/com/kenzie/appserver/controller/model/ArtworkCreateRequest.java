package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ArtworkCreateRequest {

    @NotEmpty
    @JsonProperty("artistName")
    private String artistName;

    @NotEmpty
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

    @JsonProperty("isForSale")
    private boolean isForSale;

    @Min(0)
    @JsonProperty("price")
    private Double price;

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

    public boolean isForSale() {
        return isForSale;
    }

    public void setForSale(boolean forSale) {
        isForSale = forSale;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
