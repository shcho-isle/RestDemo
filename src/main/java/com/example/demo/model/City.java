package com.example.demo.model;

public class City extends NamedEntity {

    private String country;

    private String description;

    private Double area;

    private Integer population;

    private String website;

    public City() {
    }

    public City(Integer id, String name, String country, String description, Double area, Integer population, String website) {
        super(id, name);
        this.country = country;
        this.description = description;
        this.area = area;
        this.population = population;
        this.website = website;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
