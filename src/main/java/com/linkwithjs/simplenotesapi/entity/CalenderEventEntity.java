package com.linkwithjs.simplenotesapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "events")
public class CalenderEventEntity extends BaseEntity{

    @Column(nullable = false)
    private String title;
    private Date startDateTime;
    private Date endDateTime;
    private boolean isAllDay=false;
    private String location;
    private String description;
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name="user_id")
    private UserEntity user;

}
