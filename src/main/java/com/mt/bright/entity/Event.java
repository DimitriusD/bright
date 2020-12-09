package com.mt.bright.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalTime;

@Entity
@Getter @Setter
public class Event extends BaseEntity{

    private LocalTime evenStartDate;

    private LocalTime eventEndDate;
}
