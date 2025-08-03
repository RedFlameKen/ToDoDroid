package com.lacaba.tododroid.controller;

import java.util.ArrayList;
import java.util.Collections;

import com.google.android.material.tabs.TabLayout;
import com.lacaba.tododroid.controller.todo.ToDoListController;
import com.lacaba.tododroid.model.todo.BoardType;
import com.lacaba.tododroid.model.todo.ToDo;
import com.lacaba.tododroid.model.todo.ToDoBoards;
import com.lacaba.tododroid.model.todo.ToDoList;
import com.lacaba.tododroid.view.adapter.ToDoBoardAdapter;

public class ToDoListViewController {

    private ToDoList todolist;
    private ToDoBoards boards;
    private ToDoBoardAdapter todoBoardAdapter;
    private ToDoBoardAdapter doingBoardAdapter;
    private ToDoBoardAdapter doneBoardAdapter;
    private ToDoListController todolistController;

    private TabLayout tabs;

    public static ToDoListViewController INSTANCE;

    private ToDoListViewController(){}

    public static ToDoListViewController getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ToDoListViewController();
        }
        return INSTANCE;
    }

    public void initController(ToDoList todolist, TabLayout tabs){
        this.todolist = todolist;
        this.tabs = tabs;
        todolistController = new ToDoListController();

        initBoards();
    }

    private void initBoards(){
        boards = new ToDoBoards.Builder()
            .setTodos(new ArrayList<>())
            .setDoings(new ArrayList<>())
            .setDones(new ArrayList<>())
            .build();
    }

    public void setTab(int tab){
        tabs.getTabAt(tab).select();
    }

    public void setTodoBoardAdapter(ToDoBoardAdapter todoBoardAdapter){
        this.todoBoardAdapter = todoBoardAdapter;
        todolistController.getToDosByBoardType(todolist.getId(), BoardType.TODO, 
                todos -> {
                    sortTodos(todos, BoardType.TODO);
                    for (int i = 0; i < todos.size(); i++){
                        boards.getTodos().add(todos.get(i));
                        todoBoardAdapter.notifyItemInserted(boards.getTodos().size()-1);
                    }
                });
    }

    public void setDoingBoardAdapter(ToDoBoardAdapter doingBoardAdapter){
        this.doingBoardAdapter = doingBoardAdapter;
        todolistController.getToDosByBoardType(todolist.getId(), BoardType.DOING, 
                todos -> {
                    sortTodos(todos, BoardType.DOING);
                    for (int i = 0; i < todos.size(); i++){
                        boards.getDoings().add(todos.get(i));
                        doingBoardAdapter.notifyItemInserted(boards.getDoings().size()-1);
                    }
                });
    }

    public void setDoneBoardAdapter(ToDoBoardAdapter doneBoardAdapter){
        this.doneBoardAdapter = doneBoardAdapter;
        todolistController.getToDosByBoardType(todolist.getId(), BoardType.DONE, 
                todos -> {
                    sortTodos(todos, BoardType.DONE);
                    for (int i = 0; i < todos.size(); i++){
                        boards.getDones().add(todos.get(i));
                        doneBoardAdapter.notifyItemInserted(boards.getDones().size()-1);
                    }
                });
    }

    private void sortTodos(ArrayList<ToDo> todos, BoardType type){
        Collections.sort(todos, (t1, t2) -> {
            int t1Index = getToDoIdOrderArray(type).indexOf(t1.getId());
            int t2Index = getToDoIdOrderArray(type).indexOf(t2.getId());
            if(t1Index < t2Index){
                return -1;
            } 
            if (t1Index > t2Index){
                return 1;
            }
            return 0;
        });
    }

    public void setBoardAdapter(BoardType type, ToDoBoardAdapter adapter){
        switch (type) {
            case TODO:
                setTodoBoardAdapter(adapter);
                break;
            case DOING:
                setDoingBoardAdapter(adapter);
                break;
            case DONE:
                setDoneBoardAdapter(adapter);
                break;
        }
    }

    public void addToDo(ToDo todo){
        boards.getTodos().add(todo);
    }
    
    public void addDoing(ToDo todo){
        boards.getDoings().add(todo);
    }
    
    public void addDone(ToDo todo){
        boards.getDones().add(todo);
    }

    public void updateToDo(ToDo todo){
        int position = getBoardArray(todo.getBoardType()).indexOf(todo);
        getBoardAdapter(todo.getBoardType()).notifyItemChanged(position);
        todolistController.updateToDo(todo);
    }

    public void updateToDoNoNotify(ToDo todo){
        todolistController.updateToDo(todo);
        syncToDoListOrder(todo.getBoardType());
    }


    public void migrateToDo(ToDo todo, BoardType fromType, BoardType toType, int toPosition){
        int fromPosition = removeToDo(fromType, todo);
        getBoardArray(toType).add(toPosition, todo);
        getBoardAdapter(fromType).notifyItemRemoved(fromPosition);
        getBoardAdapter(toType).notifyItemInserted(toPosition);
        todo.setBoardType(toType);
        todolistController.updateToDo(todo);
        syncToDoListOrder(fromType);
        syncToDoListOrder(toType);
    }

    public void moveToDo(BoardType type, int fromPosition, int toPosition){
        ArrayList<ToDo> boardArray = getBoardArray(type);
        Collections.swap(boardArray, fromPosition, toPosition);
        notifyAdapter(type, fromPosition, toPosition);
        syncToDoListOrder(type);
    }

    public void deleteToDo(ToDo todo){
        BoardType type = todo.getBoardType();
        int position = removeToDo(type, todo);
        notifyAdapterRemove(type, position);
        todolistController.deleteToDoFromDb(todo);
        syncToDoListOrder(type);
    }

    public void notifyAdapter(BoardType type, int fromPosition, int toPosition){
        switch (type) {
            case TODO:
                todoBoardAdapter.notifyItemMoved(fromPosition, toPosition);
                break;
            case DOING:
                doingBoardAdapter.notifyItemMoved(fromPosition, toPosition);
                break;
            case DONE:
                doneBoardAdapter.notifyItemMoved(fromPosition, toPosition);
                break;
        }
    }

    public void notifyAdapterRemove(BoardType type, int position){
        switch (type) {
            case TODO:
                todoBoardAdapter.notifyItemRemoved(position);
                break;
            case DOING:
                doingBoardAdapter.notifyItemRemoved(position);
                break;
            case DONE:
                doneBoardAdapter.notifyItemRemoved(position);
                break;
        }
    }
    public void addToDo(BoardType type, ToDo todo){
        todo.setTodolistId(todolist.getId());
        switch (type) {
            case TODO:
                addToDoAction(boards.getTodos(), todo);
                break;
            case DOING:
                addToDoAction(boards.getDoings(), todo);
                break;
            case DONE:
                addToDoAction(boards.getDones(), todo);
                break;
        }
    }

    public void notifyAdapterState(){
        getBoardAdapter(BoardType.TODO).notifyDataSetChanged();
        getBoardAdapter(BoardType.DOING).notifyDataSetChanged();
        getBoardAdapter(BoardType.DONE).notifyDataSetChanged();
    }

    private void addToDoAction(ArrayList<ToDo> todos, ToDo todo){
        todos.add(todo);
        todoBoardAdapter.notifyItemInserted(todos.size()-1);
        todolistController.writeToDoToDb(todo);
        syncToDoListOrder(todo.getBoardType());
    }

    private void syncToDoListOrder(BoardType type){
        setToDoListIdsArray(type, generateToDoIdOrderArray(getBoardArray(type)));
        todolistController.updateToDoList(todolist);
    }

    public void syncAllToDoLists(){
        syncToDoListOrder(BoardType.TODO);
        syncToDoListOrder(BoardType.DOING);
        syncToDoListOrder(BoardType.DONE);
    }

    private ArrayList<String> generateToDoIdOrderArray(ArrayList<ToDo> todos){
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < todos.size(); i++)
            ids.add(todos.get(i).getId());
        return ids;
    }

    private ArrayList<String> getToDoIdOrderArray(BoardType type){
        switch (type) {
            case TODO:
                return todolist.getTodo();
            case DOING:
                return todolist.getDoing();
            case DONE:
                return todolist.getDone();
            default:
                return null;
        }
    }

    private void setToDoListIdsArray(BoardType type, ArrayList<String> ids){
        switch (type) {
            case TODO:
                todolist.setTodo(ids);
                break;
            case DOING:
                todolist.setDoing(ids);
                break;
            case DONE:
                todolist.setDone(ids);
                break;
        }
    }

    private void addToDoAction(ArrayList<ToDo> todos, ToDo todo, int position){
        todos.add(position, todo);
        todoBoardAdapter.notifyItemInserted(position);
        todolistController.writeToDoToDb(todo);
        syncToDoListOrder(todo.getBoardType());
    }

    public void addToDo(BoardType type, ToDo todo, int position){
        switch (type) {
            case TODO:
                addToDoAction(boards.getTodos(), todo, position);
                break;
            case DOING:
                addToDoAction(boards.getDoings(), todo, position);
                break;
            case DONE:
                addToDoAction(boards.getDones(), todo, position);
                break;
        }
    }

    public int removeToDo(BoardType type, ToDo todo){
        int index = -1;
        ArrayList<ToDo> boardArray = getBoardArray(type);
        index = boardArray.indexOf(todo);
        boardArray.remove(todo);
        return index;
    }

    public ArrayList<ToDo> getBoardArray(BoardType type){
        switch (type) {
            case TODO:
                return boards.getTodos();
            case DOING:
                return boards.getDoings();
            case DONE:
                return boards.getDones();
            default:
                return null;
        }
    }
    
    public ToDoBoardAdapter getBoardAdapter(BoardType type){
        switch (type) {
            case TODO:
                return todoBoardAdapter;
            case DOING:
                return doingBoardAdapter;
            case DONE:
                return doneBoardAdapter;
            default:
                return null;
        }
    }
    
}
