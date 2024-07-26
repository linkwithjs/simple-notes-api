package com.linkwithjs.simplenotesapi.repository;

import com.linkwithjs.simplenotesapi.entity.CalenderEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CalenderEventRepository extends JpaRepository<CalenderEventEntity, Integer> {

    @Query(value = "SELECT e.*, u.email FROM events e INNER JOIN users u ON e.user_id = u.id order by e.id", nativeQuery = true)
    List<CalenderEventEntity> findAllEvents();
}
