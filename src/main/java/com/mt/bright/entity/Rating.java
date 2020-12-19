package com.mt.bright.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @Column(columnDefinition = "integer default 0")
    int total;

    @Column(columnDefinition = "integer default 0")
    int overall;
}
