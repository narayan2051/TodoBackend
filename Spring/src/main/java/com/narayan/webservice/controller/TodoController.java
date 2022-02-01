package com.narayan.webservice.controller;


import com.narayan.webservice.model.Todo;
import com.narayan.webservice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "http://localhost:4200")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @PostMapping("/{user_name}")
    public Todo postTodo(@RequestBody Todo todo, @PathVariable String user_name) {
        //call service to save ToDo
        todoService.save(todo,user_name);
        return todo;
    }

    @GetMapping("/{user_name}")
    public List<Todo> getAllTodo() {
        return todoService.getAll();
    }

    @GetMapping("/{user_name}/{id}")
    public Todo getTodo(@PathVariable("id") Long id) {
        return todoService.findById(id);
    }

    @PutMapping("/{user_name}/{todo_id}")
    public Todo updateTodo(@RequestBody Todo todo, @PathVariable("user_name") String userName, @PathVariable("todo_id") Long id) {
       return todoService.save(todo,userName);
    }

    @DeleteMapping("/{user_name}/{id}")
    public ResponseEntity<?> delete(@PathVariable("user_name") String userName, @PathVariable("id") Long id) {
        Todo todo = todoService.delete(id);
        if (todo != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
