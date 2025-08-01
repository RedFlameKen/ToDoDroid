package com.lacaba.tododroid.view.dialog;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.ToDoListViewController;
import com.lacaba.tododroid.model.todo.BoardType;
import com.lacaba.tododroid.model.todo.ToDo;
import com.lacaba.tododroid.view.component.RowButton;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    private LinearLayout buttonPanel;

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

        buttonPanel = layout.findViewById(R.id.todoexpand_button_panel);

        initButtonPanel(inflater);

        return layout;
    }

    private void saveToDo(){
        todo.setTitle(titleField.getText().toString());
        todo.setDescription(descField.getText().toString());
        controller.updateToDo(todo);
        getDialog().cancel();
    }

    private void initButtonPanel(LayoutInflater inflater){
        switch (boardType) {
            case TODO:
                initTodoButtonPanel(inflater);
                break;
            case DOING:
                initDoingButtonPanel(inflater);
                break;
            case DONE:
                initDoneButtonPanel(inflater);
                break;
        }
        RowButton deleteButton = RowButton.inflate(inflater, R.drawable.delete, "Delete");
        deleteButton.setOnClickListener(v -> {
            deleteToDoAction();
        });
        buttonPanel.addView(deleteButton);

        setFocusLostListeners();
    }

    private void initTodoButtonPanel(LayoutInflater inflater){
        RowButton doingButton = RowButton.inflate(inflater, R.drawable.arrow_right, "Move to doing");
        doingButton.setOnClickListener(v -> migrateToDoingAction());
        buttonPanel.addView(doingButton);
        RowButton doneButton = RowButton.inflate(inflater, R.drawable.check_n, "Move to Done");
        doneButton.setOnClickListener(v -> migrateToDoneAction());
        buttonPanel.addView(doneButton);
    }

    private void initDoingButtonPanel(LayoutInflater inflater){
        RowButton todoButton = RowButton.inflate(inflater, R.drawable.clipboard, "Move to Todo");
        todoButton.setOnClickListener(v -> migrateToToDoAction());
        buttonPanel.addView(todoButton);
        RowButton doneButton = RowButton.inflate(inflater, R.drawable.check_n, "Move to Done");
        doneButton.setOnClickListener(v -> migrateToDoneAction());
        buttonPanel.addView(doneButton);
    }

    private void initDoneButtonPanel(LayoutInflater inflater){
        RowButton todoButton = RowButton.inflate(inflater, R.drawable.clipboard, "Move to Todo");
        todoButton.setOnClickListener(v -> migrateToToDoAction());
        buttonPanel.addView(todoButton);
        RowButton doingButton = RowButton.inflate(inflater, R.drawable.arrow_left, "Move to doing");
        doingButton.setOnClickListener(v -> migrateToDoingAction());
        buttonPanel.addView(doingButton);
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

    private void deleteToDoAction(){
        controller.deleteToDo(todo);
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
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s){
                if(s.toString().isBlank())
                    if(saveButton.isEnabled()){
                        setSaveButtonEnabled(false);
                        return;
                    }
                if(!saveButton.isEnabled()){
                    setSaveButtonEnabled(true);
                    return;
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
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
