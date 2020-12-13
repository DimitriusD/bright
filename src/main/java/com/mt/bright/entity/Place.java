package com.mt.bright.entity;


import com.mt.bright.enums.PlaceType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Place extends BaseEntity{

    @Enumerated(value = EnumType.STRING)
    private PlaceType placeType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Lob
    private List<byte[]> images = new ArrayList<>();


}
