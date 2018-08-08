package com.example.demo.dao;

import com.example.demo.model.Attraction;

import java.util.List;

public interface AttractionDao {
    // null if updated attraction do not belong to cityId
    Attraction save(Attraction attraction, int cityId);

    // false if attraction do not belong to cityId
    boolean delete(int id, int cityId);

    // null if attraction do not belong to cityId
    Attraction get(int id, int cityId);

    // ORDERED rating
    List<Attraction> getAll(int cityId, Double fromRating, Double toRating, int offset, int limit);
}
