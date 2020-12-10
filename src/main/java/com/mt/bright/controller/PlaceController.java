package com.mt.bright.controller;


import com.mt.bright.dao.PlaceRepository;
import com.mt.bright.dto.PlaceDTO;
import com.mt.bright.entity.Place;
import com.mt.bright.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController()
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceMapper placeMapper;

    @PostMapping()
    public Place create(@ModelAttribute PlaceDTO placeDTO){

        Place place = placeMapper.toEntity(placeDTO);

        if(Objects.nonNull(place)){
           return placeRepository.save(place);
        }
        return null;
    }

    @GetMapping("/{id}")
    public Place readById(@PathVariable Long id){
        Optional<Place> placeById = placeRepository.findById(id);
        return placeById.orElse(null);

    }

    @PutMapping("/{id}")
    public void update(@RequestBody PlaceDTO placeDTO, @PathVariable Long id){
        placeRepository.findById(id)
                .map((place) -> {
                    Place updatedPlace = placeMapper.toEntity(placeDTO);
                    return placeRepository.save(updatedPlace);
                })
                .orElseGet(() ->
                        {
                            Place newPlace = placeMapper.toEntity(placeDTO);
                            return placeRepository.save(newPlace);
                        }
                );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        placeRepository.deleteById(id);
    }


}
