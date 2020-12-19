package com.mt.bright.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    private String location;

    private String description;

    @OneToOne
    private Rating rating;

    @ElementCollection(fetch = FetchType.EAGER)
    @Lob
    private List<byte[]> images = new ArrayList<>();
}
