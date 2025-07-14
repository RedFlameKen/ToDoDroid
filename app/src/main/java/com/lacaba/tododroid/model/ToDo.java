package com.lacaba.tododroid.model;

import java.util.Date;

public class ToDo {

    private int id;
    private String title;
    private String description;
    private boolean isDone;
    private Date dueDate;
    private int todolistId;
    
    private ToDo(Builder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.isDone = builder.isDone;
        this.dueDate = builder.dueDate;
        this.todolistId = builder.todolistId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int getTodolistId() {
        return todolistId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public static class Builder {

        private int id;
        private String title;
        private String description;
        private boolean isDone;
        private Date dueDate;
        private int todolistId;
    
        public Builder(){
            id = todolistId = -1;
            isDone = false;
            title = description = "";
            dueDate = null;
        }

        public Builder id(int id){
            this.id = id;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder isDone(boolean isDone){
            this.isDone = isDone;
            return this;
        }

        public Builder dueDate(Date dueDate){
            this.dueDate = dueDate;
            return this;
        }

        public Builder todolistId(int todolistId){
            this.todolistId = todolistId;
            return this;
        }

        public ToDo build(){
            return new ToDo(this);
        }

    }

}
