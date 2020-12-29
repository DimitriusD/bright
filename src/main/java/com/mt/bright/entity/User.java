package com.mt.bright.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    private String surname;

    private Integer age;

    private String phone;

    private String pass;

    @ElementCollection
    private List<String> interest = new ArrayList<>();
}
