package ru.kalach.smvc.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Beer {
    private int beerId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters")
    private String beerTitle;

    @NotEmpty
    private String beerStyle;

    @NotEmpty(message = "Brewery should not be empty")
    private String brewery;

    @Min(value = 0, message = "Abv should be greater than 0")
    private double abv;

    public Beer() {

    };

    public Beer(String beerStyle) {
        this.beerStyle = beerStyle;
    };

    public String getBeerStyle() {
        return beerStyle;
    }

    public void setBeerStyle(String beerStyle) {
        this.beerStyle = beerStyle;
    }

    public int getBeerId() {
        return beerId;
    }

    public void setBeerId(int beerId) {
        this.beerId = beerId;
    }

    public String getBeerTitle() {
        return beerTitle;
    }

    public void setBeerTitle(String beerTitle) {
        this.beerTitle = beerTitle;
    }

    public Beer(int beerId, String beerTitle, String beerStyle, String brewery, double abv) {
        this.beerId = beerId;
        this.beerTitle = beerTitle;
        this.beerStyle = beerStyle;
        this.brewery = brewery;
        this.abv = abv;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public double getAbv() {
        return abv;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }
}
