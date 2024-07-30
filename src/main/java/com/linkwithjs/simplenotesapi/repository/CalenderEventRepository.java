package com.linkwithjs.simplenotesapi.repository;

//import com.linkwithjs.simplenotesapi.dto.CalenderEventDTO;
import com.linkwithjs.simplenotesapi.entity.CalenderEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
//import java.util.Optional;

@Repository
public interface CalenderEventRepository extends JpaRepository<CalenderEventEntity, Integer> {

    @Query(value = "SELECT e.*, u.email FROM events e INNER JOIN users u ON e.user_id = u.id order by e.id", nativeQuery = true)
    List<CalenderEventEntity> findAllEvents();

//    @Query(value = "SELECT e.*, u.email FROM events e INNER JOIN users u ON e.user_id = u.id where e.id = :id", nativeQuery = true)
//    CalenderEventEntity findByEventId(Integer id);
//    @Query(value = "select e.* from events e where e.id = :id", nativeQuery = true)
//    Optional<CalenderEventEntity> findById(int id);

    List<CalenderEventEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
