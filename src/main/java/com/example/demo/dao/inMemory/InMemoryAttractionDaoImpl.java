package com.example.demo.dao.inMemory;

import com.example.demo.dao.AttractionDao;
import com.example.demo.model.Attraction;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryAttractionDaoImpl implements AttractionDao {

    private final Map<Integer, Map<Integer, Attraction>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
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

    @Override
    public boolean delete(int id, int cityId) {
        Map<Integer, Attraction> attractions = repository.get(cityId);
        return attractions != null && attractions.remove(id) != null;
    }

    @Override
    public Attraction get(int id, int cityId) {
        Map<Integer, Attraction> attractions = repository.get(cityId);
        return attractions == null ? null : attractions.get(id);
    }

    @Override
    public List<Attraction> getAll(int cityId, Double fromRating, Double toRating, int offset, int limit) {
        Map<Integer, Attraction> attractions = repository.get(cityId);

        if (attractions == null) {
            attractions = Collections.emptyMap();
        }

        return attractions.values().stream()
                .filter(a -> a.getRating() >= fromRating && a.getRating() <= toRating)
                .sorted(Comparator.comparing(Attraction::getRating).reversed())
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }
}
