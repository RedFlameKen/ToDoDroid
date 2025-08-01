package com.lacaba.tododroid.model.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

public class User {

    @DocumentId
    private String id;

    private String username;

    private ArrayList<DocumentReference> todolists;

    public User(){}

    private User(Builder builder){
        id = builder.id;
        username = builder.username;
        todolists = builder.todolists;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<DocumentReference> getTodolists() {
        return todolists;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTodolists(ArrayList<DocumentReference> todolists) {
        this.todolists = todolists;
    }

    @Exclude
    public Map<String, Object> getUpdateMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("todolists", todolists);
        return map;
    }

    public static class Builder {

        private String id;
        private String username;
        private ArrayList<DocumentReference> todolists;

        public Builder(){
            id = username = "";
            todolists = null;
        }

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder setTodolists(ArrayList<DocumentReference> todolists) {
            this.todolists = todolists;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

}
