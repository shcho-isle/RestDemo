package com.example.demo.dao.inMemory;

import com.example.demo.dao.CityDao;
import com.example.demo.model.City;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryCityDaoImpl implements CityDao {

    private final Map<Integer, City> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        save(new City(null, "Lviv", "Ukraine", null, null, null, null));
        save(new City(null, "Kyiv", "Ukraine", null, null, null, null));
        save(new City(null, "Paris", "France", null, null, null, null));
        save(new City(null, "Rivne", "Ukraine", null, null, null, null));
        save(new City(null, "Leon", "France", null, null, null, null));
    }

    @Override
    public City save(City city) {
        if (city.isNew()) {
            city.setId(counter.incrementAndGet());
        }
        repository.put(city.getId(), city);
        return city;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public City get(int id) {
        return repository.get(id);
    }

    @Override
    public List<City> getAll(String country, int offset, int limit) {
        return repository.values().stream()
                .filter(city -> country == null || city.getCountry().equals(country))
                .sorted(Comparator.comparing(City::getCountry).thenComparing(City::getName))
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getCount() {
        return repository.size();
    }
}
