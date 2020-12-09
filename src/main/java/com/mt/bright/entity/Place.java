package com.mt.bright.entity;


import com.mt.bright.enums.PlaceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Place extends BaseEntity{

    @Enumerated(value = EnumType.STRING)
    private PlaceType placeType;

    @Lob
    private byte[] placeImage;


}
