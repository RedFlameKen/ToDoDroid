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

public class MessageDialog extends DialogFragment {

    public static final String TAG = "MessageDialog";

    private String messageText;
    private String okText;

    private TextView messageLabel;

    private Button okButton;

    private MessageDialog(Builder builder){
        messageText = builder.messageText;
        okText = builder.okText;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.layout_confirm_dialog, container, false);

        messageLabel = layout.findViewById(R.id.confirmdialog_message);
        messageLabel.setText(messageText);

        okButton = layout.findViewById(R.id.confirmdialog_ok_button);
        okButton.setText(okText);

        okButton.setOnClickListener(view -> dismiss());

        return layout;
    }

    @Override
    public void onStart(){
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static class Builder {

        private String messageText;
        private String okText;

        public Builder(){
            messageText="Input";
            okText = "OK";
        }

        public Builder setOkText(String okText) {
            this.okText = okText;
            return this;
        }

        public Builder setMessageText(String messageText) {
            this.messageText = messageText;
            return this;
        }

        public MessageDialog build(){
            return new MessageDialog(this);
        }

    }

}
