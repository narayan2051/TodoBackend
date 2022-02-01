package com.narayan.webservice.service;

import com.narayan.webservice.model.Todo;

import java.util.List;

public interface TodoService {
    Todo save(Todo todo, String userName);
    Todo update(Todo todo, Long id, Long userName);
    Todo delete(Long id);
    List<Todo> getAll();

    Todo findById(Long id);
}
