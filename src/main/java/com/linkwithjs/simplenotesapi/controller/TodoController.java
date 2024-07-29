package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.dto.TodoDTO;
import com.linkwithjs.simplenotesapi.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class TodoController {
    @Autowired
    TodoService todoService;

    @PostMapping("/create-todo")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todos) {
        return ResponseEntity.ok(todoService.createTodo(todos));
    }

    @GetMapping("/get-todos")
    public ResponseEntity<?> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PatchMapping("/todo-completed/{id}")
    public ResponseEntity<?> todoCompleted(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(todoService.todoCompleted(id));
    }

    @DeleteMapping("delete-todo/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(todoService.deleteTodo(id));
    }

    @PutMapping("/update-todo/{id}")
    public ResponseEntity<ReqRes> updateTodo(@PathVariable(value="id") int todoId, @RequestBody TodoDTO todos){
        return ResponseEntity.ok(todoService.updateTodo(todoId, todos));
    }

    @PatchMapping("/change-status/{id}")
    public ResponseEntity<ReqRes> changeStatusToProgress(@PathVariable(value = "id") int todoId, @RequestBody TodoDTO todo){
        return ResponseEntity.ok(todoService.changeStatus(todoId, todo));
    }


}
