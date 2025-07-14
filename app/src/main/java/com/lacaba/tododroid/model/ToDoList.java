package com.lacaba.tododroid.model;

public class ToDoList {

    private int id;
    private String name;
    private int workspaceId;

    private ToDoList(Builder builder){
        id = builder.id;
        name = builder.name;
        workspaceId = builder.workspaceId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public static class Builder {

        private int id;
        private String name;
        private int workspaceId;

        public Builder(){
            workspaceId = id = -1;
            name = "";
        }

        public Builder id(int id){
            this.id = id;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder workspaceId(int workspaceId){
            this.workspaceId = workspaceId;
            return this;
        }

        public ToDoList build(){
            return new ToDoList(this);
        }

    }

}
