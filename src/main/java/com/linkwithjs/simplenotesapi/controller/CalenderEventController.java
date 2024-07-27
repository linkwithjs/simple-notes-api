package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.dto.CalenderEventDTO;
import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.service.CalenderEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class CalenderEventController {
    @Autowired
    private CalenderEventService calenderEventService;

    @PostMapping("/create-event")
    public ResponseEntity<?> creatEvent(@RequestBody CalenderEventDTO events) {
        return ResponseEntity.ok(calenderEventService.createEvent(events));
    }

    @GetMapping("/get-events")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllEvents() {
        return ResponseEntity.ok(calenderEventService.readAllEvents());
    }

    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<ReqRes> deleteEvent(@PathVariable(value = "id") int eventId) {
        ReqRes response = calenderEventService.deleteEvent(eventId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update-event/{id}")
    public ResponseEntity<ReqRes> updateEvent(@PathVariable(value = "id") int eventId, @RequestBody CalenderEventDTO events) {
        return ResponseEntity.ok(calenderEventService.updateEvent(eventId, events));
    }

    //    An endpoint to change isAllDay status
    @PatchMapping("/change-day/{id}")
    public ResponseEntity<?> changeIsAllDay(@PathVariable(value = "id") int eventId) {
        return ResponseEntity.ok(calenderEventService.changeIsAllDay(eventId));
    }

    @GetMapping("/get-single-event/{id}")
    public ResponseEntity<?> getSingleEvent(@PathVariable(value="id") int eventId){
        return ResponseEntity.ok(calenderEventService.getSingleEvent(eventId));
    }

}
