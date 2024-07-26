package com.linkwithjs.simplenotesapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "events")

public class CalenderEventEntity extends BaseEntity{

    @Column(nullable = false)
    private String title;
    private Date startDateTime;
    private Date endDateTime;
    private boolean isAllDay=false;
    private String location;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="user_id")
    private UserEntity user;

}
