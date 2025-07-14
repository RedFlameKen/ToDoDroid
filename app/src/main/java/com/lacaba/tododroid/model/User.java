package com.lacaba.tododroid.model;

public class User {

    private int id;
    private String username;

    private User(Builder builder){
        id = builder.id;
        username = builder.username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public static class Builder {

        private int id;
        private String username;

        public Builder(){
            id = -1;
            username = "";
        }

        public Builder id(int id){
            this.id = id;
            return this;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

}
