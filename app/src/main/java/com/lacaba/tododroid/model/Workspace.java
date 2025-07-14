package com.lacaba.tododroid.model;

public class Workspace {

    private int id;
    private String name;
    private int userId;

    private Workspace(Builder builder){
        id = builder.id;
        name = builder.name;
        userId = builder.userId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public static class Builder {

        private int id;
        private String name;
        private int userId;

        public Builder(){
            userId = id = -1;
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

        public Builder userId(int userId){
            this.userId = userId;
            return this;
        }

        public Workspace build(){
            return new Workspace(this);
        }


    }
    
}
