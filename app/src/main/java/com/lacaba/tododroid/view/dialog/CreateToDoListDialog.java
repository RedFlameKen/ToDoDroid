package com.lacaba.tododroid.view.dialog;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.view.dialog.event.OnToDoListCreateListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public class CreateToDoListDialog extends DialogFragment {

    public static final String TAG = "CreateToDoListDialog";

    private Button submitButton;
    private Button cancelButton;
    private EditText inputField;

    private OnToDoListCreateListener onToDoListCreateListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_create_todolist, container, false);
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));


        submitButton = layout.findViewById(R.id.createtodolist_submit_button);
        cancelButton = layout.findViewById(R.id.createtodolist_cancel_button);

        inputField = layout.findViewById(R.id.createtodolist_input_field);

        setListeners();

        return layout;
    }

    @Override
    public void onStart(){
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void setListeners(){
        submitButton.setOnClickListener(view -> {
            String name = inputField.getText().toString();
            if(onToDoListCreateListener != null)
                onToDoListCreateListener.onToDoListCreate(name);
            dismiss();
        });
        cancelButton.setOnClickListener(view -> {
            getDialog().cancel();
        });
    }

    public void setOnToDoListCreateListener(OnToDoListCreateListener onToDoListCreateListener) {
        this.onToDoListCreateListener = onToDoListCreateListener;
    }
    
}
