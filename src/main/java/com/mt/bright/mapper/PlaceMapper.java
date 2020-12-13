package com.mt.bright.mapper;

import com.mt.bright.dto.PlaceDTO;
import com.mt.bright.entity.Place;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class PlaceMapper {

    private ModelMapper mapper;

    public Place toEntity(PlaceDTO dto, List<MultipartFile> images) {
        Place place =  Objects.isNull(dto)
                ? null
                : mapper.map(dto, Place.class);
        if(Objects.nonNull(place) &&    Objects.nonNull(images) && !images.isEmpty()){
            images.forEach(image -> {
                try {
                    place.getImages().add(image.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return place;
    }

    public Place toEntity(PlaceDTO dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, Place.class);
    }

    public PlaceDTO toDto(Place entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, PlaceDTO.class);
    }

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }


}
