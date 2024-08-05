package com.linkwithjs.simplenotesapi.controller;


import com.linkwithjs.simplenotesapi.dto.CategoryDTO;
import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.dto.TopicDTO;
import com.linkwithjs.simplenotesapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @GetMapping("/get-category")
    public ResponseEntity<?> getAllTopics() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping("delete-category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") int categoryId) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @GetMapping("get-single-category/{id}")
    public ResponseEntity<?> getCategory(@PathVariable(value = "id") int categoryId){
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @PatchMapping("/update-category/{id}")
    public ResponseEntity<ReqRes> updateCategory(@PathVariable(value = "id") int categoryId, @RequestBody CategoryDTO category) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, category));
    }


}
