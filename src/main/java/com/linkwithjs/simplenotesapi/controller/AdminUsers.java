package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.entity.Note;
import com.linkwithjs.simplenotesapi.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUsers {
    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/get-note")
    public ResponseEntity<Object> getAllNotes() {
        return ResponseEntity.ok(noteRepository.findAll());
    }

    @PostMapping("/create-note")
    public ResponseEntity<?> addNote(@RequestBody ReqRes noteRequest) {
        Note note = new Note();
        note.setTitle(noteRequest.getName());
        return ResponseEntity.ok(noteRepository.save(note));
    }

//    Only Users can access this API
    @GetMapping("/user")
    public ResponseEntity<Object> users() {
        return ResponseEntity.ok("Only Users can access this API.");
    }

//    Users and admin both can access this API
    @GetMapping("/adminuser")
    public ResponseEntity<Object> adminUser() {
        return ResponseEntity.ok("Both admin and users can access this API.");
    }

}
