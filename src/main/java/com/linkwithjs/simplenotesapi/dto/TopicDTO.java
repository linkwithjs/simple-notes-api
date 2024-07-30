package com.linkwithjs.simplenotesapi.dto;

import com.linkwithjs.simplenotesapi.entity.CategoryEntity;
import com.linkwithjs.simplenotesapi.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class TopicDTO {
   private String title;
   private String description;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
   private UserEntity user;
   private String email;
   private List<CategoryEntity> categories;
}
