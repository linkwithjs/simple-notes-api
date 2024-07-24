package com.linkwithjs.simplenotesapi.repository;

import com.linkwithjs.simplenotesapi.entity.CalenderEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalenderEventRepository extends JpaRepository<CalenderEventEntity, Integer> {

}
