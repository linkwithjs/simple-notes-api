package com.linkwithjs.simplenotesapi.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CalenderEventDTO {
    private String title;
    private Date startDateTime;
    private Date endDateTime;
    private boolean isAllDay;
    private String location;
    private String description;
    private String email;
    private LocalDateTime createdAt;
    private boolean isDeleted;

}
