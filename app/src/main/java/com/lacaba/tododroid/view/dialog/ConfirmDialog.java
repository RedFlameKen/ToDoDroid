package com.lacaba.tododroid.view.dialog;

import com.lacaba.tododroid.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public class ConfirmDialog extends DialogFragment {

    public static final String TAG = "ConfirmDialog";

    private String messageText;
    private String okText;

    private TextView messageLabel;

    private Button cancelButton;
    private Button okButton;

    private Runnable onOkListener;

    private ConfirmDialog(Builder builder){
        messageText = builder.messageText;
        okText = builder.okText;
        onOkListener = builder.onOkListener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.layout_confirm_dialog, container, false);

        messageLabel = layout.findViewById(R.id.confirmdialog_message);
        messageLabel.setText(messageText);

        okButton = layout.findViewById(R.id.confirmdialog_ok_button);
        okButton.setText(okText);

        cancelButton = layout.findViewById(R.id.confirmdialog_cancel_button);

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
                onOkListener.run();
            dismiss();
        });
        cancelButton.setOnClickListener(view -> {
            dismiss();
        });
    }

    public static class Builder {

        private String messageText;
        private String okText;

        private Runnable onOkListener;

        public Builder(){
            messageText="Input";
            okText = "OK";
            onOkListener = null;
        }

        public Builder setOkText(String okText) {
            this.okText = okText;
            return this;
        }

        public Builder setMessageText(String messageText) {
            this.messageText = messageText;
            return this;
        }

        public Builder setOnOkListener(Runnable onOkListener) {
            this.onOkListener = onOkListener;
            return this;
        }

        public ConfirmDialog build(){
            return new ConfirmDialog(this);
        }

    }

}
