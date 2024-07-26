package com.linkwithjs.simplenotesapi.dto;

import com.linkwithjs.simplenotesapi.entity.UserEntity;
import lombok.Data;

@Data
public class TopicDTO {
   private String title;
   private String description;
   private UserEntity user;
}
