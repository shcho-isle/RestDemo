package com.example.demo.web;

import com.example.demo.dao.CityDao;
import com.example.demo.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.example.demo.util.ValidationUtil.checkIdConsistent;
import static com.example.demo.util.ValidationUtil.checkNew;
import static com.example.demo.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(CityRestController.REST_URL)
public class CityRestController {
    static final String REST_URL = "/rest/cities";

    private final CityDao dao;

    @Autowired
    public CityRestController(CityDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<City> getAllUsers(){
        return dao.getAll();
    }

    @PostMapping
    public ResponseEntity<City> create(@RequestBody City city) {
        checkNew(city);
        City created = dao.save(city);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{id}")
    public City get(@PathVariable("id") int id) {
        return checkNotFoundWithId(dao.get(id), id);
    }

    @PostMapping("/{id}")
    public void update(@RequestBody City city, @PathVariable("id") int id) {
        checkIdConsistent(city, id);
        dao.save(city);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        checkNotFoundWithId(dao.delete(id), id);
    }
}
