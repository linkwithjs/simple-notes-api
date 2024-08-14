package com.linkwithjs.simplenotesapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sections")
public class SectionEntity extends BaseEntity{
    private String title;
    private String sypnosis;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private TopicEntity topic;
}
