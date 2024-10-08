package ru.kalach.smvc.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "beers")
public class Beer {
    @Id
    @Column(name = "beerid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int beerId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters")
    @Column(name = "beertitle")
    private String beerTitle;

    @NotEmpty
    @Column(name = "beerstyle")
    private String beerStyle;

    @NotEmpty(message = "Brewery should not be empty")
    @Column(name = "brewery")
    private String brewery;

    @Min(value = 0, message = "Abv should be greater than 0")
    @Column(name = "abv")
    private double abv;

    // Пустой конструктор нужен спрингу для работы

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

    public Beer(String beerTitle, String beerStyle, String brewery, double abv) {
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
