package com.linkwithjs.simplenotesapi.dto;

import com.linkwithjs.simplenotesapi.enums.Status;
import lombok.Data;

@Data
public class TodoDTO {
    private String title;
    private Status status;
}
