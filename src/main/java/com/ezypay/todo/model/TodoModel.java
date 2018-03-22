package com.ezypay.todo.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class TodoModel {
    @Getter(AccessLevel.PUBLIC)
    private String taskName;

    public String name() {
        return this.getTaskName();
    }
}