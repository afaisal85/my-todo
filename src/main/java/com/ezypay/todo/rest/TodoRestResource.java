package com.ezypay.todo.rest;

import com.ezypay.todo.model.TodoModel;
import com.ezypay.todo.persistence.TodoRepository;
import com.ezypay.todo.persistence.entity.TodoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@RequestMapping("/api/todos")
public class TodoRestResource {
    @Autowired
    private TodoRepository todoRepository;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createTask(@RequestBody TodoModel todo) {
        String taskName = todo.name();
        TodoEntity entity = new TodoEntity(taskName, false, true);
        todoRepository.save(entity);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTasks() {
        Iterable<TodoEntity> iterable = todoRepository.findAll();
        List<TodoEntity> todoList = StreamSupport.stream(iterable.spliterator(), false)
                .filter(TodoEntity::getIsDisplayed)
                .collect(Collectors.toList());

        return ResponseEntity.ok(todoList);
    }

    @PutMapping(value = "/done", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity done(@RequestParam(value="id") Long id,
                               @RequestParam(value="taskName") String taskName) {
        TodoEntity todoEntity = new TodoEntity(id, taskName, true, true);
        todoRepository.save(todoEntity);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/clean", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity clean() {
        Iterable<TodoEntity> iterable = todoRepository.findAll();
        List<TodoEntity> completedList = StreamSupport.stream(iterable.spliterator(), false)
                .filter(TodoEntity::getIsCompleted)
                .filter(TodoEntity::getIsDisplayed)
                .map(todo -> new TodoEntity(todo.getId(), todo.getTaskName(), true, false))
                .collect(Collectors.toList());

        completedList.stream().forEach(todo -> todoRepository.save(todo));
        return ResponseEntity.ok().build();
    }
}