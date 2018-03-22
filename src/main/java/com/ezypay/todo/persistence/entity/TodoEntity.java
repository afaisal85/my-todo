package com.ezypay.todo.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "todo_table")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "taskName")
    private String taskName;

    @Column(name = "isCompleted")
    private boolean isCompleted;

    @Column(name = "isDisplayed")
    private boolean isDisplayed;

    public TodoEntity() {}

    public TodoEntity(String taskName,
                      boolean isCompleted,
                      boolean isDisplayed) {
        this.taskName = taskName;
        this.isCompleted = isCompleted;
        this.isDisplayed = isDisplayed;
    }

    public TodoEntity(long id,
                      String taskName,
                      boolean isCompleted,
                      boolean isDisplayed) {
        this.id = id;
        this.taskName = taskName;
        this.isCompleted = isCompleted;
        this.isDisplayed = isDisplayed;
    }

    public Long getId() {
        return this.id;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public boolean getIsCompleted() {
        return this.isCompleted;
    }

    public boolean getIsDisplayed() {
        return this.isDisplayed;
    }
}