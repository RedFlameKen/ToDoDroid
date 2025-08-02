package com.lacaba.tododroid.view.component;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.view.event.OnOkListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewToDoInput extends FrameLayout {

    private EditText inputField;
    private ImageButton okButton;

    public NewToDoInput(@NonNull Context context) {
        super(context);
    }

    public NewToDoInput(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NewToDoInput(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NewToDoInput(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        inputField = findViewById(R.id.todoinput_input_field);
        okButton = findViewById(R.id.todoinput_ok_button);
    }

    public String getText() {
        String out = inputField.getText().toString();
        inputField.setText("");
        return out;
    }

    public void setOnOkListener(OnOkListener onOkListener){
        okButton.setOnClickListener(view -> onOkListener.onOk(getText()));
        setOnFocusChangeListener((view, isFocused) -> {
            if(!isFocused()){
                if(onOkListener != null){
                    onOkListener.onOk(getText());
                }
            }
        });
    }

    public static NewToDoInput inflate(LayoutInflater inflater){
        NewToDoInput layout = (NewToDoInput) inflater.inflate(R.layout.layout_todo_input, null, false);
        return layout;
    }

}
