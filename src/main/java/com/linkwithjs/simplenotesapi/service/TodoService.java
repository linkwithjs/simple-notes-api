package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.dto.TodoDTO;
import com.linkwithjs.simplenotesapi.entity.TodoEntity;
import com.linkwithjs.simplenotesapi.exception.CustomException;
import com.linkwithjs.simplenotesapi.repository.TodoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ReqRes createTodo(TodoDTO todos) {
        ReqRes resp = new ReqRes();
        try {
            TodoEntity todoEntity = new TodoEntity();
            todoEntity.setTitle(todos.getTitle());
            TodoEntity saveTodo = todoRepository.save(todoEntity);
            if (saveTodo.getId() != null) {
                resp.setData(saveTodo);
                resp.setMessage("Todo added successfully.");
            } else {
                resp.setMessage("Todo could not add.");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ResponseEntity<?> getAllTodos() {
        try {
            List<TodoDTO> todos = todoRepository.findAll().stream()
                    .map(todo -> modelMapper.map(todo, TodoDTO.class))
                    .collect(Collectors.toList());
            return ReqRes.successResponse("Todos fecthed successfully.", todos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ReqRes.successResponse("Failed to fetch topics", e.getMessage()));
        }
    }

    public ReqRes todoCompleted(int id) {
        ReqRes resp = new ReqRes();
        try {
            TodoEntity todo = todoRepository.findById(id).orElseThrow(() ->
                    new CustomException("Todo not found for this id: " + id));
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
            resp.setData(todo);
            resp.setMessage("Todo completed.");
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes deleteTodo(int id) {
        ReqRes resp = new ReqRes();
        try {
            TodoEntity todo = todoRepository.findById(id).orElseThrow(() -> new CustomException("Topic not found for this id: " + id));
            todoRepository.delete(todo);
            resp.setStatusCode(200);
            resp.setData(todo);
            resp.setMessage("Todo Deleted Successfully.");
        } catch (CustomException e) {
            resp.setStatusCode(404);
            resp.setError(e.getMessage());
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes updateTodo(int id, TodoDTO todo) {
        ReqRes resp = new ReqRes();
        try {
            TodoEntity todos = todoRepository.findById(id).orElseThrow(() ->
                    new CustomException("Todo not found for this id: ", id));
            todos.setTitle(todo.getTitle());
            TodoEntity saveTodo = todoRepository.save(todos);
            if (saveTodo.getId() != null) {
                resp.setData(saveTodo);
                resp.setMessage("Todo updated successfully.");
            } else {
                resp.setMessage("Todo could not update.");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }
}
