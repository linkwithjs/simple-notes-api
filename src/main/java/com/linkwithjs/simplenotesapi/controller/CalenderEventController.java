package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.dto.CalenderEventDTO;
import com.linkwithjs.simplenotesapi.service.CalenderEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class CalenderEventController {
    @Autowired
    private CalenderEventService calenderEventService;
    @PostMapping("/create-event")
    public ResponseEntity<?> creatEvent(@RequestBody CalenderEventDTO events) {
        return ResponseEntity.ok(calenderEventService.createEvent(events));
    }

}
