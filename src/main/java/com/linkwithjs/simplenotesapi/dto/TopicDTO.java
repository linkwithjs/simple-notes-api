package com.linkwithjs.simplenotesapi.dto;

import com.linkwithjs.simplenotesapi.entity.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TopicDTO {
   private String title;
   private String description;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
   private UserEntity user;
   private String email;
}
