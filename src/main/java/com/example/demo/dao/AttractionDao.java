package com.example.demo.dao;

import com.example.demo.model.Attraction;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AttractionDao {

    private final Map<Integer, Map<Integer, Attraction>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public Attraction save(Attraction attraction, int cityId) {
        if (attraction.isNew()) {
            attraction.setId(counter.incrementAndGet());
        } else if (get(attraction.getId(), cityId) == null) {
            return null;
        }
        Map<Integer, Attraction> attractions = repository.computeIfAbsent(cityId, ConcurrentHashMap::new);
        attractions.put(attraction.getId(), attraction);
        return attraction;
    }

    public boolean delete(int id, int cityId) {
        Map<Integer, Attraction> attractions = repository.get(cityId);
        return attractions != null && attractions.remove(id) != null;
    }

    public Attraction get(int id, int cityId) {
        Map<Integer, Attraction> attractions = repository.get(cityId);
        return attractions == null ? null : attractions.get(id);
    }

    public Collection<Attraction> getAll(int cityId) {
        return getAllAsStream(cityId).collect(Collectors.toList());
    }

    public Collection<Attraction> getBetween(Double fromRating, Double toRating, int cityId) {
        return getAllAsStream(cityId)
                .filter(a -> a.getRating() >= fromRating && a.getRating() <= toRating)
                .collect(Collectors.toList());
    }

    private Stream<Attraction> getAllAsStream(int cityId) {
        Map<Integer, Attraction> attractions = repository.get(cityId);
        return attractions == null ?
                Stream.empty() : attractions.values().stream().sorted(Comparator.comparing(Attraction::getRating).reversed());
    }
}
