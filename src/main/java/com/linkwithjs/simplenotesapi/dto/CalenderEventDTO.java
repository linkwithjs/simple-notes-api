package com.linkwithjs.simplenotesapi.dto;

import com.linkwithjs.simplenotesapi.entity.UserEntity;
import lombok.Data;
import java.util.Date;

@Data
public class CalenderEventDTO {
    private String title;
    private Date startDateTime;
    private Date endDateTime;
    private boolean isAllDay;
    private String location;
    private String description;
    private UserEntity user;
}
