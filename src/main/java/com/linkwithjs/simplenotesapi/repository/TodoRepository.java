package com.linkwithjs.simplenotesapi.repository;

import com.linkwithjs.simplenotesapi.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {
}
