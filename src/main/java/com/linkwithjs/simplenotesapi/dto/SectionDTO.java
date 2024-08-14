package com.linkwithjs.simplenotesapi.dto;

import com.linkwithjs.simplenotesapi.entity.CategoryEntity;
import com.linkwithjs.simplenotesapi.entity.TopicEntity;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class SectionDTO {
    private String title;
    private String sypnosis;
    private String notes;
    private List<TopicEntity> topics;
}
