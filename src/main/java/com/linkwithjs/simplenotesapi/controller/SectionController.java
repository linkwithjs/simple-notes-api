package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.dto.SectionDTO;
import com.linkwithjs.simplenotesapi.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
