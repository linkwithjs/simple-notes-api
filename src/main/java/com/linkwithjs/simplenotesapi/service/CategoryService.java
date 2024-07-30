package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.CategoryDTO;
import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.entity.CategoryEntity;
import com.linkwithjs.simplenotesapi.repository.CategoryRepository;
import com.linkwithjs.simplenotesapi.repository.TopicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TopicRepository topicRepository;

    @Transactional
    public ReqRes createCategory(CategoryDTO categoryDTO) {
        ReqRes resp = new ReqRes();
        try {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setTopics(categoryDTO.getTopics());
            CategoryEntity saveCategory = categoryRepository.save(categoryEntity);
            if (saveCategory.getId() != null) {
                resp.setData(saveCategory);
                resp.setMessage("Category added successfully.");
            } else {
                resp.setMessage("Category could not add.");
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }

        return resp;
    }
}
