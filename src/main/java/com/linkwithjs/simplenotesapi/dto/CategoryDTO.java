package com.linkwithjs.simplenotesapi.dto;

import com.linkwithjs.simplenotesapi.entity.TopicEntity;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private String name;
    private List<TopicEntity> topics;
}
