package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.dto.TopicDTO;
import com.linkwithjs.simplenotesapi.entity.TopicEntity;
import com.linkwithjs.simplenotesapi.entity.UserEntity;
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

}
