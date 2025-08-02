package com.lacaba.tododroid.view.dialog;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.DashboardController;
import com.lacaba.tododroid.model.todo.ToDoList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;

public class ToDoListItemOptionsDialog extends DialogFragment {

    public static final String TAG = "ToDoListItemOptionsDialog";

    private TextView headerLabel;
    private Button renameButton;
    private Button deleteButton;

    private ToDoList todolist;
    private DashboardController dashboardController;

    public ToDoListItemOptionsDialog(ToDoList todolist, DashboardController dashboardController){
        this.todolist = todolist;
        this.dashboardController = dashboardController;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.layout_todolist_options_dialog, container, false);

        headerLabel = layout.findViewById(R.id.todolistoption_header);

        headerLabel.setText(todolist.getName());

        renameButton = layout.findViewById(R.id.todolistoption_rename);
        renameButton.setOnClickListener(view -> {
            renameButtonAction();
            getDialog().hide();
        });

        deleteButton = layout.findViewById(R.id.todolistoption_delete);
        deleteButton.setOnClickListener(view -> {
            dashboardController.deleteToDoList(todolist);
            getDialog().dismiss();
        });

        return layout;
    }
    
    @Override
    public void onStart(){
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void renameButtonAction(){
        PromptDialog dialog = new PromptDialog.Builder()
            .setHeaderText("Rename ToDoList")
            .setInputText(todolist.getName())
            .setHintText("New name")
            .setOkText("Rename")
            .setOnOkListener(text -> {
                renameToDoList(text);
            })
            .build();
        dialog.show(getParentFragmentManager(), PromptDialog.TAG);
    }

    private void renameToDoList(String newname){
        ConfirmDialog dialog = new ConfirmDialog.Builder()
            .setOkText("yes")
            .setMessageText(String.format("Are you sure you want to rename this todolist to \"%s\"?", newname))
            .setOnOkListener(() -> {
                dashboardController.renameToDoList(todolist, newname);
                dismiss();
            })
            .build();
        dialog.show(getParentFragmentManager(), ConfirmDialog.TAG);
    }

}
