package com.mt.bright.controller;


import com.mt.bright.dao.PlaceRepository;
import com.mt.bright.dto.PlaceDTO;
import com.mt.bright.entity.Place;
import com.mt.bright.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController()
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private PlaceMapper placeMapper;

    @GetMapping()
    public List<PlaceDTO> readAll(){
        List<PlaceDTO> placeDTO = new ArrayList<>();
        Iterable<Place> places = placeRepository.findAll();
        if(Objects.nonNull(places)){
            places.forEach(place -> {
                placeDTO.add(placeMapper.toDto(place));
            });
        }
        return placeDTO;

    }

    @GetMapping("/{id}")
    public PlaceDTO readById(@PathVariable Long id){
        Optional<Place> placeById = placeRepository.findById(id);
        return placeById.map(place -> placeMapper.toDto(place)).orElse(null);

    }

    @PostMapping()
    public PlaceDTO create(@ModelAttribute PlaceDTO placeDTO){

        Place place = placeMapper.toEntity(placeDTO);

        if(Objects.nonNull(place)){
            Place save = placeRepository.save(place);
            placeDTO.setId(save.getId());
            return placeDTO;
        }
        return null;
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
