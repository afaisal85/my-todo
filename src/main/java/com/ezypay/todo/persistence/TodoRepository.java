package com.ezypay.todo.persistence;

import com.ezypay.todo.persistence.entity.TodoEntity;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository <TodoEntity, Long> {

}