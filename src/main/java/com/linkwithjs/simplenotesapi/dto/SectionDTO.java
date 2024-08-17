package com.linkwithjs.simplenotesapi.dto;

import com.linkwithjs.simplenotesapi.entity.TopicEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SectionDTO {

    private String title;
    private String sypnosis;
    private String notes;
    private List<TopicEntity> topics;
}
