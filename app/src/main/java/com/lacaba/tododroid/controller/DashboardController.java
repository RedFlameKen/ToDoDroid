package com.lacaba.tododroid.controller;

import java.util.ArrayList;

import com.lacaba.tododroid.controller.todo.ToDoListController;
import com.lacaba.tododroid.model.ResourceRepository;
import com.lacaba.tododroid.model.todo.ToDoList;
import com.lacaba.tododroid.view.adapter.ToDoListAdapter;

public class DashboardController {

    private ToDoListController todolistController;
    private ArrayList<ToDoList> todolists;
    private ToDoListAdapter todolistAdapter;

    public DashboardController(ArrayList<ToDoList> todolists){
        this.todolistController = new ToDoListController();
        this.todolists = todolists;
    }

    public void setTodolistAdapter(ToDoListAdapter todolistAdapter) {
        this.todolistAdapter = todolistAdapter;
    }

    public void addToDoList(String todolistName){
        ToDoList todolist = todolistController.createToDoList(todolistName);
        todolists.add(0, todolist);
        if(todolistAdapter != null)
            todolistAdapter.notifyItemInserted(0);
        todolistController.writeToDoListToDb(todolist);
    }

    public void renameToDoList(ToDoList todolist, String newname){
        int index = todolists.indexOf(todolist);
        todolist.setName(newname);
        todolistAdapter.notifyItemChanged(index);
        todolistController.updateToDoList(todolist);
    }

    public void deleteToDoList(ToDoList todolist){
        int index = removeToDoList(todolist);
        todolistAdapter.notifyItemRemoved(index);
        todolistController.deleteToDoList(todolist);
    }

    private int removeToDoList(ToDoList todolist){
        int index = todolists.indexOf(todolist);
        todolists.remove(todolist);
        return index;
    }

    public void reloadToDoLists(Runnable post){
        String userId = ResourceRepository.getInstance().getCurUser().getId();
        todolistController.getToDoLists(userId, todolists -> {
            this.todolists = todolists;
            todolistAdapter.notifyDataSetChanged();
            if(post != null)
                post.run();
        });
    }

    public ArrayList<ToDoList> getTodolists() {
        return todolists;
    }

    
}
