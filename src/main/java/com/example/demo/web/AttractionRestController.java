package com.example.demo.web;

import com.example.demo.dao.AttractionDao;
import com.example.demo.model.Attraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.util.ValidationUtil.*;

@RestController
@RequestMapping("/rest/cities/{cityId}/attractions")
public class AttractionRestController {
    private final AttractionDao dao;

    @Autowired
    public AttractionRestController(AttractionDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Attraction> getAll(@PathVariable("cityId") int cityId,
                                   @RequestParam(value = "fromRating", required = false) Double fromRating,
                                   @RequestParam(value = "toRating", required = false) Double toRating,
                                   @RequestParam(value = "offset", required = false) Integer offset,
                                   @RequestParam(value = "limit", required = false) Integer limit) {
        offset = validate(offset, 0, 0, 1_000_000);
        limit = validate(limit, 3, 0, 100);

        return dao.getAll(cityId,
                fromRating != null ? fromRating : Double.MIN_VALUE,
                toRating != null ? toRating : Double.MAX_VALUE,
                offset,
                limit);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Attraction create(@PathVariable("cityId") int cityId,
                             @RequestBody Attraction attraction) {
        checkNew(attraction);
        return dao.save(attraction, cityId);
    }

    @GetMapping("/{id}")
    public Attraction get(@PathVariable("cityId") int cityId,
                          @PathVariable("id") int id) {
        return checkNotFoundWithId(dao.get(id, cityId), id);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Attraction update(@RequestBody Attraction attraction,
                             @PathVariable("cityId") int cityId,
                             @PathVariable("id") int id) {
        checkIdConsistent(attraction, id);
        return dao.save(attraction, cityId);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("cityId") int cityId,
                       @PathVariable("id") int id) {
        checkNotFoundWithId(dao.delete(id, cityId), id);
    }
}
