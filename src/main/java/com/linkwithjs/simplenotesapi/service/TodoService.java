package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.dto.TodoDTO;
import com.linkwithjs.simplenotesapi.entity.TodoEntity;
import com.linkwithjs.simplenotesapi.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public ReqRes createTodo(TodoDTO todos){
        ReqRes resp = new ReqRes();
        try{
            TodoEntity todoEntity = new TodoEntity();
            todoEntity.setTitle(todos.getTitle());
            TodoEntity saveTodo = todoRepository.save(todoEntity);
            if(saveTodo.getId()!=null){
                resp.setData(saveTodo);
                resp.setMessage("Todo added successfully.");
            }else {
                resp.setMessage("Todo could not add.");
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }
}
