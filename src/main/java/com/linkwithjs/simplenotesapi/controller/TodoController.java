package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.dto.TodoDTO;
import com.linkwithjs.simplenotesapi.service.TodoService;
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
public class TodoController {
    @Autowired
    TodoService todoService;
    @PostMapping("/create-todo")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todos){
        return ResponseEntity.ok(todoService.createTodo(todos));
    }
}
