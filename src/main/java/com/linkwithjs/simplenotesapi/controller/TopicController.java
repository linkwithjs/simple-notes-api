package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.dto.TopicDTO;
import com.linkwithjs.simplenotesapi.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete-topic/{id}")
    public ResponseEntity<ReqRes> deleteEvent(@PathVariable(value = "id") int topicId) {
        ReqRes response = topicService.deleteTopic(topicId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update-topic/{id}")
    public ResponseEntity<ReqRes> updateTopic(@PathVariable(value="id") int topicId, @RequestBody TopicDTO topics){
        return ResponseEntity.ok(topicService.updateTopic(topicId, topics));
    }

    @PatchMapping("/change-delete-status/{id}")
    public ResponseEntity<ReqRes> changeDeleteStatus(@PathVariable(value="id") int topicId ){
        return ResponseEntity.ok(topicService.changeIsDelete(topicId));
    }


}
