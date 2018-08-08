package com.example.demo.web;

import com.example.demo.dao.CityDao;
import com.example.demo.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.util.ValidationUtil.*;

@RestController
@RequestMapping("/rest/cities")
public class CityRestController {

    private final CityDao dao;

    @Autowired
    public CityRestController(CityDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<City> getAll(@RequestParam(value = "country", required = false) String country,
                             @RequestParam(value = "offset", required = false) Integer offset,
                             @RequestParam(value = "limit", required = false) Integer limit) {
        offset = validate(offset, 0, 0, 1_000_000);
        limit = validate(limit, 3, 0, 100);

        return dao.getAll(country, offset, limit);
    }

    @GetMapping(value = "/count")
    public Integer getCitiesCount() {
        return dao.getCount();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public City create(@RequestBody City city) {
        checkNew(city);
        return dao.save(city);
    }

    @GetMapping("/{id}")
    public City get(@PathVariable("id") int id) {
        return checkNotFoundWithId(dao.get(id), id);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public City update(@RequestBody City city, @PathVariable("id") int id) {
        checkIdConsistent(city, id);
        return dao.save(city);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        checkNotFoundWithId(dao.delete(id), id);
    }
}
