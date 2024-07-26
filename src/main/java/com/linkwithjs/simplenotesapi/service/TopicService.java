package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.dto.TopicDTO;
import com.linkwithjs.simplenotesapi.entity.TopicEntity;
import com.linkwithjs.simplenotesapi.entity.UserEntity;
import com.linkwithjs.simplenotesapi.exception.CustomException;
import com.linkwithjs.simplenotesapi.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    public ReqRes createTopic(TopicDTO topic) {
        ReqRes resp = new ReqRes();

        try {
            TopicEntity topicEntity = new TopicEntity();
            topicEntity.setTitle(topic.getTitle());
            topicEntity.setDescription(topic.getTitle());

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
}
