package com.lacaba.tododroid.controller;

import java.util.ArrayList;

import com.lacaba.tododroid.controller.todo.ToDoListController;
import com.lacaba.tododroid.model.ResourceRepository;
import com.lacaba.tododroid.model.todo.ToDoList;
import com.lacaba.tododroid.util.Consumer;
import com.lacaba.tododroid.view.adapter.ToDoListAdapter;

public class DashboardController {

    private static DashboardController INSTANCE;

    private ToDoListController todolistController;
    private ArrayList<ToDoList> todolists;
    private ToDoListAdapter todolistAdapter;

    public static DashboardController getInstance(){
        if(INSTANCE == null){
            INSTANCE = new DashboardController();
            INSTANCE.initController();
        }
        return INSTANCE;
    }

    private DashboardController(){
    }

    public void initController(){
        todolistController = new ToDoListController();
        todolists = new ArrayList<>();
    }

    public void loadToDoList(Consumer<DashboardController> onFinishInit){
        String userId = ResourceRepository.getInstance().getCurUser().getId();
        todolistController.getToDoLists(userId, todolists -> {
            this.todolists = todolists;
            if(onFinishInit != null)
                onFinishInit.accept(this);
            todolistAdapter.notifyDataSetChanged();
        });
    }

    public DashboardController(ArrayList<ToDoList> todolists){
        this.todolistController = new ToDoListController();
        this.todolists = todolists;
    }

    public void setToDoLists(ArrayList<ToDoList> todolists){
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

    public ToDoListAdapter getTodolistAdapter() {
        return todolistAdapter;
    }

}
