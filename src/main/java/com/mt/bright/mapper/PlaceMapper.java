package com.mt.bright.mapper;

import com.mt.bright.dto.PlaceDTO;
import com.mt.bright.entity.Place;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;

@Component
public class PlaceMapper {

    private ModelMapper mapper;

    @PostConstruct
    public void setupMapper() {
        
        mapper.createTypeMap(PlaceDTO.class, Place.class)
                .addMappings(m -> m.skip(Place::setPlaceImage)).setPostConverter(toEntityConverter());
    }

   private Converter<PlaceDTO, Place> toEntityConverter() {
        return context -> {

            PlaceDTO source = context.getSource();
            Place destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }


    public Place toEntity(PlaceDTO dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Place.class);
    }

    public PlaceDTO toDto(Place entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, PlaceDTO.class);
    }

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    private void mapSpecificFields(PlaceDTO source, Place destination) {
        byte[] bytes = null;
        try {
            if(Objects.nonNull(source.getPlaceImage())){
                bytes = source.getPlaceImage().getBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        destination.setPlaceImage(bytes);
    }

}
