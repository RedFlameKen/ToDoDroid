package com.lacaba.tododroid.view.dialog;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.view.event.OnOkListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public class PromptDialog extends DialogFragment {

    public static final String TAG = "PromptDialog";

    private String headerText;
    private String hintText;
    private String okText;

    private TextView headerLabel;
    private EditText inputField;

    private Button cancelButton;
    private Button okButton;

    private OnOkListener onOkListener;

    private PromptDialog(Builder builder){
        headerText = builder.headerText;
        hintText = builder.hintText;
        okText = builder.okText;
        onOkListener = builder.onOkListener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.layout_prompt_dialog, container, false);

        headerLabel = layout.findViewById(R.id.promptdialog_label);
        headerLabel.setText(headerText);

        inputField = layout.findViewById(R.id.promptdialog_input_field);
        inputField.setHint(hintText);

        okButton = layout.findViewById(R.id.promptdialog_submit_button);
        okButton.setText(okText);

        cancelButton = layout.findViewById(R.id.promptdialog_cancel_button);

        setListeners();

        return layout;
    }

    @Override
    public void onStart(){
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void setListeners(){
        okButton.setOnClickListener(view -> {
            if(onOkListener != null)
                onOkListener.onOk(inputField.getText().toString());
            dismiss();
        });
        cancelButton.setOnClickListener(view -> {
            dismiss();
        });
    }

    public static class Builder {

        private String headerText;
        private String hintText;
        private String okText;

        private OnOkListener onOkListener;

        public Builder(){
            headerText="Input";
            okText = "OK";
            hintText = "";
            onOkListener = null;
        }

        public Builder setOkText(String okText) {
            this.okText = okText;
            return this;
        }

        public Builder setHintText(String hintText) {
            this.hintText = hintText;
            return this;
        }

        public Builder setHeaderText(String headerText) {
            this.headerText = headerText;
            return this;
        }

        public Builder setOnOkListener(OnOkListener onOkListener) {
            this.onOkListener = onOkListener;
            return this;
        }

        public PromptDialog build(){
            return new PromptDialog(this);
        }

    }

}
