package com.mt.bright.controller;


import com.mt.bright.dao.PlaceRepository;
import com.mt.bright.dto.PlaceDTO;
import com.mt.bright.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RestController()
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private PlaceRepository placeRepository;

    @PostMapping()
    public Place create(@ModelAttribute PlaceDTO placeDTO){
        return null;
    }

    @GetMapping("/{id}")
    public Place readById(@PathVariable Long id){
        Optional<Place> placeById = placeRepository.findById(id);
        return placeById.orElse(null);

    }

    @PutMapping("/{id}")
    public void update(@RequestBody Place newPlace, @PathVariable Long id){
        placeRepository.findById(id)
                .map(place -> {
                    place.setLocation(newPlace.getLocation());
                    place.setName(newPlace.getName());
                    place.setPlaceType(newPlace.getPlaceType());
                    return placeRepository.save(place);
                })
                .orElseGet(() ->
                        {
                            newPlace.setId(id);
                            return placeRepository.save(newPlace);
                        }
                );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        placeRepository.deleteById(id);
    }


}
