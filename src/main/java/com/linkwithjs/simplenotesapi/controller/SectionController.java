package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.dto.SectionDTO;
import com.linkwithjs.simplenotesapi.dto.TopicDTO;
import com.linkwithjs.simplenotesapi.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class SectionController {
    @Autowired
    SectionService sectionService;

    @PostMapping("/create-section")
    public ResponseEntity<?> createSection(@RequestBody SectionDTO section) {
        return ResponseEntity.ok(sectionService.createSection(section));
    }

    @GetMapping("/get-sections")
    public ResponseEntity<?> getAllTopics() {
        return ResponseEntity.ok(sectionService.getAllSections());
    }

    @DeleteMapping("/delete-section/{id}")
    public ResponseEntity<ReqRes> deleteSection(@PathVariable(value = "id") int sectionId) {
        ReqRes response = sectionService.deleteSection(sectionId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update-section/{id}")
    public ResponseEntity<ReqRes> updateSection(@PathVariable(value = "id") int sectionId, @RequestBody SectionDTO sections) {
        return ResponseEntity.ok(sectionService.updateSection(sectionId, sections));
    }
}
