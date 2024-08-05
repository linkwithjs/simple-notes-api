package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.CategoryDTO;
import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.entity.CategoryEntity;
import com.linkwithjs.simplenotesapi.exception.CustomException;
import com.linkwithjs.simplenotesapi.repository.CategoryRepository;
import com.linkwithjs.simplenotesapi.repository.TopicRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ReqRes createCategory(CategoryDTO categoryDTO) {
        ReqRes resp = new ReqRes();
        try {
            CategoryEntity categoryEntity = new CategoryEntity();
//            categoryEntity.setTopics(categoryDTO.getTopics());
            categoryEntity.setName(categoryDTO.getName());
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

    public ReqRes deleteCategory(int id) {
        ReqRes resp = new ReqRes();
        try {
            CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() ->
                    new CustomException("Category not found for this id: " + id));
            categoryRepository.delete(categoryEntity);
            resp.setStatusCode(200);
            resp.setData(categoryEntity);
            resp.setMessage("Category deleted Successfully.");
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ResponseEntity<?> getAllCategories() {
        try {
            List<CategoryDTO> categories = categoryRepository.findAll().stream()
                    .map(category -> modelMapper.map(category, CategoryDTO.class))
                    .collect(Collectors.toList());
            return ReqRes.successResponse("Category fetched successfully.", categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ReqRes.successResponse("Failed to fetch topics", e.getMessage()));
        }

    }
}
