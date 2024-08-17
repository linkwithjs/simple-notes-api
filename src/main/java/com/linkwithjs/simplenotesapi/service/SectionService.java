package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.dto.SectionDTO;
import com.linkwithjs.simplenotesapi.dto.TopicDTO;
import com.linkwithjs.simplenotesapi.entity.SectionEntity;
import com.linkwithjs.simplenotesapi.entity.TopicEntity;
import com.linkwithjs.simplenotesapi.entity.UserEntity;
import com.linkwithjs.simplenotesapi.exception.CustomException;
import com.linkwithjs.simplenotesapi.repository.SectionRepository;
import lombok.Builder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Service
public class SectionService {
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    ModelMapper modelMapper;

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

    public ResponseEntity<?> getAllSections() {
        try {
            List<SectionDTO> sections = sectionRepository.findAll().stream()
                    .map(section -> modelMapper.map(section, SectionDTO.class))
                    .collect(Collectors.toList());
            return ReqRes.successResponse("Sections fetched successfully.", sections);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ReqRes.successResponse("Failed to fetch Sections", e.getMessage()));
        }
    }

    public ReqRes deleteSection(int id) {
        ReqRes resp = new ReqRes();
        try {
            SectionEntity section = sectionRepository.findById(id).orElseThrow(() -> new CustomException("Section not found for this id: " + id));
            sectionRepository.delete(section);
            resp.setStatusCode(200);
            resp.setData(section);
            resp.setMessage("Section Deleted Successfully.");
        } catch (CustomException e) {
            resp.setStatusCode(404);
            resp.setError(e.getMessage());
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes updateSection(int id, SectionDTO section) {
        ReqRes resp = new ReqRes();
        try {
            SectionEntity sections = sectionRepository.findById(id).orElseThrow(() ->
                    new CustomException("Topic not found for this id: ", id));
            sections.setTitle(section.getTitle());
            sections.setSypnosis(section.getSypnosis());
            sections.setNotes(section.getNotes());

            SectionEntity saveSection = sectionRepository.save(sections);
            if (saveSection.getId() != null) {
                resp.setData(saveSection);
                resp.setMessage("Section updated successfully.");
            } else {
                resp.setMessage("Section could not update.");
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

}
