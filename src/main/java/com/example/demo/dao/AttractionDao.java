package com.example.demo.dao;

import com.example.demo.model.Attraction;

import java.util.Collection;

public interface AttractionDao {
    // null if updated meal do not belong to cityId
    Attraction save(Attraction attraction, int cityId);

    // false if meal do not belong to cityId
    boolean delete(int id, int cityId);

    // null if meal do not belong to cityId
    Attraction get(int id, int cityId);

    // ORDERED rating
    Collection<Attraction> getAll(int cityId);

    // ORDERED dateTime
    Collection<Attraction> getBetween(Double fromRating, Double toRating, int cityId);

    default Attraction getWithCity(int id, int cityId) {
        throw new UnsupportedOperationException();
    }
}
