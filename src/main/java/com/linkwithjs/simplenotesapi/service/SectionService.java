package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.dto.SectionDTO;
import com.linkwithjs.simplenotesapi.entity.SectionEntity;
import com.linkwithjs.simplenotesapi.repository.SectionRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Builder
@Service
public class SectionService {
    @Autowired
    SectionRepository sectionRepository;

    public ReqRes createSection(SectionDTO section) {
        ReqRes resp = new ReqRes();
        try {
            SectionEntity sectionEntity = new SectionEntity();
            sectionEntity.setTitle(section.getTitle());
            sectionEntity.setSypnosis(section.getSypnosis());
            sectionEntity.setNotes(section.getNotes());
            SectionEntity saveSection = sectionRepository.save(sectionEntity);
            if (saveSection.getId() != null) {
                resp.setData(saveSection);
                resp.setMessage("Section added successfully.");
            } else {
                resp.setMessage("Section could not add.");
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }
}
