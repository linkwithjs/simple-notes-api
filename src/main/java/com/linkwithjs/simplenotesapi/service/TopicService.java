package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.dto.TopicDTO;
import com.linkwithjs.simplenotesapi.entity.CategoryEntity;
import com.linkwithjs.simplenotesapi.entity.TopicEntity;
import com.linkwithjs.simplenotesapi.entity.UserEntity;
import com.linkwithjs.simplenotesapi.exception.CustomException;
import com.linkwithjs.simplenotesapi.repository.CategoryRepository;
import com.linkwithjs.simplenotesapi.repository.TopicRepository;
import lombok.Builder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Service
public class TopicService {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Autowired
    private ModelMapper modelMapper;

    public ReqRes createTopic(TopicDTO topic) {
        ReqRes resp = new ReqRes();
        try {
//            CategoryEntity categoryEntity = categoryRepository.findAllById()
            TopicEntity topicEntity = new TopicEntity();
            topicEntity.setTitle(topic.getTitle());
            topicEntity.setDescription(topic.getDescription());
            topicEntity.setCategoryEntities(topic.getCategories());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Check if the authentication object is not null and is authenticated
            if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
                UserEntity user = (UserEntity) authentication.getPrincipal();
                topicEntity.setUser(user);
                TopicEntity saveTopic = topicRepository.save(topicEntity);
                if (saveTopic.getId() != null) {
                    resp.setData(saveTopic);
                    resp.setMessage("Topic added successfully.");
                } else {
                    resp.setMessage("Topic could not add.");
                }
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ResponseEntity<?> getAllTopics() {
        try {
            List<TopicDTO> topics = topicRepository.findAll().stream()
                    .map(topic -> modelMapper.map(topic, TopicDTO.class))
                    .collect(Collectors.toList());
            return ReqRes.successResponse("Topics fetched successfully.", topics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ReqRes.successResponse("Failed to fetch topics", e.getMessage()));
        }

    }

    public ReqRes deleteTopic(int id) {
        ReqRes resp = new ReqRes();
        try {
            TopicEntity topic = topicRepository.findById(id).orElseThrow(() -> new CustomException("Topic not found for this id: " + id));
            topicRepository.delete(topic);
            resp.setStatusCode(200);
            resp.setData(topic);
            resp.setMessage("Topic Deleted Successfully.");
        } catch (CustomException e) {
            resp.setStatusCode(404);
            resp.setError(e.getMessage());
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes updateTopic(int id, TopicDTO topic) {
        ReqRes resp = new ReqRes();
        try {
            TopicEntity topics = topicRepository.findById(id).orElseThrow(() ->
                    new CustomException("Topic not found for this id: ", id));
            topics.setTitle(topic.getTitle());
            topics.setDescription(topic.getDescription());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Check if the authentication object is not null and is authenticated
            if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
                UserEntity user = (UserEntity) authentication.getPrincipal();
                topics.setUser(user);
                TopicEntity saveTopic = topicRepository.save(topics);
                if (saveTopic.getId() != null) {
                    resp.setData(saveTopic);
                    resp.setMessage("Topic updated successfully.");
                } else {
                    resp.setMessage("Topic could not update.");
                }
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes changeIsDelete(int id) {
        ReqRes resp = new ReqRes();
        try {
            TopicEntity topic = topicRepository.findById(id).orElseThrow(() ->
                    new CustomException("Topic not found for this id: " + id));
            topic.setDeleted(!topic.isDeleted());
            topicRepository.save(topic);
            resp.setData(topic);
            resp.setMessage("Topic delete status changed.");
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ResponseEntity<?> searchByTitle(String title) {
        try {
            List<TopicEntity> topics = topicRepository.findByTitleContainingIgnoreCase(title);
            return ReqRes.successResponse("Topics fetched successfully.",
                    topics.stream().map(this::prepareTopic).collect(Collectors.toList()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ReqRes.successResponse("Failed to fetch topics", e.getMessage()));
        }
    }

    TopicDTO prepareTopic(TopicEntity topic){
        return  TopicDTO.builder().email(topic.getUser().getEmail()).createdAt(topic.getCreatedAt())
                .title(topic.getTitle()).description(topic.getDescription()).updatedAt(topic.getUpdatedAt())
                .build();
    }

}
