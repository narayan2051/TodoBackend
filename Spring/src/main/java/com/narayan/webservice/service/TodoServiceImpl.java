package com.narayan.webservice.service;

import com.narayan.webservice.model.Todo;
import com.narayan.webservice.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sun.lwawt.macosx.CSystemTray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    @Autowired
    TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo save(Todo todo, String userName) {
        if (todo.getId() >= 1) {
            Todo todo1 = findById(todo.getId());
            todo1.setDate(todo.getDate());
            todo1.setDescription(todo.getDescription());
            todo1.setDone(todo.isDone());
            todoRepository.save(todo1);
            return todo1;
        }
        todoRepository.save(todo);
        return todo;
    }

    @Override
    public Todo update(Todo todo, Long id, Long userName) {
        return null;
    }

    @Override
    public Todo delete(Long id) {
        Todo todo = findById(id);
        todoRepository.delete(todo);
        return todo;
    }


    public Todo findById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

   // @Scheduled(cron = "0/2 * * * * *")
    @Override
    public List<Todo> getAll() {
        System.out.println("===================================>Cron Job started");
        return todoRepository.findAll();
    }

}
