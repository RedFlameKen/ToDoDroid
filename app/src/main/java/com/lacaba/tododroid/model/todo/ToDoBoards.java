package com.lacaba.tododroid.model.todo;

import java.util.ArrayList;

public class ToDoBoards {

    private ArrayList<ToDo> todos;
    private ArrayList<ToDo> doings;
    private ArrayList<ToDo> dones;
    
    private ToDoBoards(Builder builder){
        todos = builder.todos;
        doings = builder.doings;
        dones = builder.dones;
    }
    
    public ArrayList<ToDo> getTodos() {
        return todos;
    }

    public ArrayList<ToDo> getDones() {
        return dones;
    }

    public ArrayList<ToDo> getDoings() {
        return doings;
    }

    public void pushTodos(ArrayList<ToDo> todos){
        for (int i = 0; i < todos.size(); i++)
            this.todos.add(todos.get(i));
    }

    public void pushDoings(ArrayList<ToDo> doings){
        for (int i = 0; i < doings.size(); i++)
            this.doings.add(doings.get(i));
    }

    public void pushDones(ArrayList<ToDo> dones){
        for (int i = 0; i < dones.size(); i++)
            this.dones.add(dones.get(i));
    }

    public static class Builder {

        private ArrayList<ToDo> todos;
        private ArrayList<ToDo> doings;
        private ArrayList<ToDo> dones;
    
        public Builder(){
            dones = doings = todos = null;
        }

        public Builder setTodos(ArrayList<ToDo> todos) {
            this.todos = todos;
            return this;
        }

        public Builder setDoings(ArrayList<ToDo> doings) {
            this.doings = doings;
            return this;
        }

        public Builder setDones(ArrayList<ToDo> dones) {
            this.dones = dones;
            return this;
        }

        public ToDoBoards build(){
            return new ToDoBoards(this);
        }

    }

}
