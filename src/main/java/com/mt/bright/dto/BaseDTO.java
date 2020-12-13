package com.mt.bright.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class BaseDTO {

    private long id;

    private String name;

    private String location;

    private String description;

    private List<byte[]> images = new ArrayList<>();

}
