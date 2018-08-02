package com.example.demo.dao;

import com.example.demo.model.City;

import java.util.List;

public interface CityDao {
    City save(City city);

    // false if not found
    boolean delete(int id);

    // null if not found
    City get(int id);

    // ORDERED country, name
    List<City> getAll();
}
