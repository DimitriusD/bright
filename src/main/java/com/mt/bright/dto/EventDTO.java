package com.mt.bright.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class EventDTO {

    private String name;

    private String location;

    private String description;

    private MultipartFile placeImage;

}
