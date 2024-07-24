package com.linkwithjs.simplenotesapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "events")

public class CalenderEventEntity extends BaseEntity{

    private String title;
    private Date startDateTime;
    private Date endDateTime;
    private boolean isAllDay=false;
    private String location;
    private String description;

}
