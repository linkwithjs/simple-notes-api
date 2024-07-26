package com.linkwithjs.simplenotesapi.repository;

import com.linkwithjs.simplenotesapi.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {

}
