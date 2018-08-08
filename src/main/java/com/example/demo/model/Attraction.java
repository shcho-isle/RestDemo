package com.example.demo.model;

public class Attraction extends NamedEntity {

    private Double rating;

    private City city;

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
