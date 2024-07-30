package com.linkwithjs.simplenotesapi.repository;

import com.linkwithjs.simplenotesapi.dto.CategoryDTO;
import com.linkwithjs.simplenotesapi.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

}
