package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.dto.CalenderEventDTO;
import com.linkwithjs.simplenotesapi.dto.TopicDTO;
import com.linkwithjs.simplenotesapi.service.TopicService;
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
public class TopicController {

    @Autowired
    TopicService topicService;

    @PostMapping("/create-topic")
    public ResponseEntity<?> createTopic(@RequestBody TopicDTO topic) {
        return ResponseEntity.ok(topicService.createTopic(topic));
    }

}
