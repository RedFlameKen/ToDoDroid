package com.lacaba.tododroid.controller.todo;

import java.util.ArrayList;
import java.util.Date;

import com.lacaba.tododroid.model.ResourceRepository;
import com.lacaba.tododroid.model.todo.BoardType;
import com.lacaba.tododroid.model.todo.ToDo;
import com.lacaba.tododroid.model.todo.ToDoList;
import com.lacaba.tododroid.repository.ToDoListRepository;
import com.lacaba.tododroid.repository.ToDoRepository;
import com.lacaba.tododroid.util.Consumer;

public class ToDoListController {

    private ToDoListRepository todolistRepository;
    private ToDoRepository todoRepository;

    public ToDoListController(){
        todolistRepository = new ToDoListRepository();
        todoRepository = new ToDoRepository();
    }

    public void getToDoLists(String userId, Consumer<ArrayList<ToDoList>> onResult){
        todolistRepository.getToDoListsByUID(userId, onResult);
    }

    public void createToDoList(String name){
        String userId = ResourceRepository.getInstance().getCurUser().getId();
        ToDoList todolist = new ToDoList.Builder()
            .name(name)
            .userId(userId)
            .todo(new ArrayList<String>())
            .doing(new ArrayList<String>())
            .done(new ArrayList<String>())
            .build();
        todolistRepository.writeToDoList(todolist, null, null);
    }

    public void writeToDoToDb(ToDo todo){
        todoRepository.writeToDo(todo, null, null);
    }

    public void findToDoListById(String todolistId, Consumer<ToDoList> onResult){
        todolistRepository.findToDoListById(todolistId, onResult);
    }

    public void getToDosByBoardType(String todolistId, BoardType boardType, Consumer<ArrayList<ToDo>> onResult){
        todoRepository.getToDosByBoardType(todolistId, boardType, onResult);
    }

    public void getToDosOrder(String todolistId, BoardType boardType, Consumer<ArrayList<String>> onResult){
        todolistRepository.getToDoOrder(todolistId, boardType, onResult);
    }

    public void updateToDo(ToDo todo){
        todoRepository.updateToDo(todo, null, null);
    }

    public void updateToDoList(ToDoList todolist){
        todolist.setUpdateDate(new Date());
        todolistRepository.updateToDoList(todolist, null, null);
    }

    public void deleteToDoFromDb(ToDo todo){
        todoRepository.deleteToDoById(todo.getId(), null, null);
    }
    
}
