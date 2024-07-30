package com.linkwithjs.simplenotesapi.repository;

import com.linkwithjs.simplenotesapi.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {
        List<TopicEntity> findByTitleContainingIgnoreCase(String title);
}
