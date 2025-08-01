package com.lacaba.tododroid.view.dialog;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.ToDoListViewController;
import com.lacaba.tododroid.model.todo.BoardType;
import com.lacaba.tododroid.model.todo.ToDo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public class ExpandToDoDialog extends DialogFragment {

    public static final String TAG = "ExpandToDoDialog";

    private ToDo todo;
    private ToDoListViewController controller;
    private BoardType boardType;
    private Button saveButton;
    private EditText descField;
    private EditText titleField;

    public ExpandToDoDialog(ToDo todo, ToDoListViewController controller, BoardType boardType){
        this.todo = todo;
        this.controller = controller;
        this.boardType = boardType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_todo_expand, container, false);
        ImageButton closeButton = layout.findViewById(R.id.todoexpand_close_button);
        closeButton.setOnClickListener(view -> {
            getDialog().cancel();
        });

        saveButton = layout.findViewById(R.id.todoexpand_save_button);
        saveButton.setOnClickListener(view -> saveToDo());

        titleField = layout.findViewById(R.id.todoexpand_title_field);
        titleField.setText(todo.getTitle());

        descField = layout.findViewById(R.id.todoexpand_desc_field);
        descField.setText(todo.getDescription());

        FrameLayout buttonPanel = layout.findViewById(R.id.todoexpand_button_panel);

        initButtonPanel(inflater, buttonPanel);

        return layout;
    }

    private void saveToDo(){
        todo.setTitle(titleField.getText().toString());
        todo.setDescription(descField.getText().toString());
        controller.updateToDo(todo);
        getDialog().cancel();
    }

    private void initButtonPanel(LayoutInflater inflater, FrameLayout buttonPanel){
        View view = null;
        switch (boardType) {
            case TODO:
                view = initTodoButtonPanel(inflater, buttonPanel);
                break;
            case DOING:
                view = initDoingButtonPanel(inflater, buttonPanel);
                break;
            case DONE:
                view = initDoneButtonPanel(inflater, buttonPanel);
                break;
        }
        buttonPanel.addView(view);

        setFocusLostListeners();
    }

    private View initTodoButtonPanel(LayoutInflater inflater, FrameLayout buttonPanel){
        View view = inflater.inflate(R.layout.layout_todoboard_expand_todo, null);
        Button doingButton = view.findViewById(R.id.todoexpandtodo_move_to_doing_button);
        Button doneButton = view.findViewById(R.id.todoexpandtodo_move_to_done_button);
        doingButton.setOnClickListener(v -> migrateToDoingAction());
        doneButton.setOnClickListener(v -> migrateToDoneAction());
        return view;
    }

    private View initDoingButtonPanel(LayoutInflater inflater, FrameLayout buttonPanel){
        View view = inflater.inflate(R.layout.layout_todoboard_expand_doing, null);
        Button todoButton = view.findViewById(R.id.todoexpanddoing_move_to_todo_button);
        Button doneButton = view.findViewById(R.id.todoexpanddoing_move_to_done_button);
        todoButton.setOnClickListener(v -> migrateToToDoAction());
        doneButton.setOnClickListener(v -> migrateToDoneAction());
        return view;
    }

    private View initDoneButtonPanel(LayoutInflater inflater, FrameLayout buttonPanel){
        View view = inflater.inflate(R.layout.layout_todoboard_expand_done, null);
        Button todoButton = view.findViewById(R.id.todoexpanddone_move_to_todo_button);
        Button doingButton = view.findViewById(R.id.todoexpanddone_move_to_doing_button);
        todoButton.setOnClickListener(v -> migrateToToDoAction());
        doingButton.setOnClickListener(v -> migrateToDoingAction());
        return view;
    }

    private void migrateToToDoAction(){
        controller.migrateToDo(todo, boardType, BoardType.TODO, 0);
        controller.setTab(0);
        getDialog().cancel();
    }

    private void migrateToDoingAction(){
        controller.migrateToDo(todo, boardType, BoardType.DOING, 0);
        controller.setTab(1);
        getDialog().cancel();
    }

    private void migrateToDoneAction(){
        controller.migrateToDo(todo, boardType, BoardType.DONE, 0);
        controller.setTab(2);
        getDialog().cancel();
    }

    @Override
    public void onStart(){
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    private String prevTitle;
    private String prevDesc;

    private void setFocusLostListeners(){
        prevDesc = prevTitle = "";
        titleField.setOnFocusChangeListener((view, isFocused) -> {
            String curTitle = descField.getText().toString();
            if(isFocused){
                prevTitle = curTitle;
                setSaveButtonEnabled(true);
                return;
            }
            if(curTitle.isBlank() || curTitle.equals(prevTitle)){
                setSaveButtonEnabled(false);
                return;
            }
        });
        descField.setOnFocusChangeListener((view, isFocused) -> {
            String curDesc = descField.getText().toString();
            if(isFocused){
                prevDesc = curDesc;
                setSaveButtonEnabled(true);
                return;
            }
            if(curDesc.isBlank() || curDesc.equals(prevDesc)){
                setSaveButtonEnabled(false);
                return;
            }
        });
    }

    private void setSaveButtonEnabled(boolean b){
        saveButton.setEnabled(b);
        saveButton.setBackgroundColor(getResources().getColor(b ?
                    R.color.gruvbox_green : R.color.gruvbox_green_grayed, null));
    }
    
}
