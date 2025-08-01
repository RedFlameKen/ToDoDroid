package com.lacaba.tododroid.view.activity;

import com.lacaba.tododroid.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ContactSupportActivity extends AppCompatActivity {

    private EditText subjectField;
    private EditText descField;
    private Button submitButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);
        initComponents();
    }

    private void initComponents(){
        subjectField = findViewById(R.id.contact_subject_field);
        descField = findViewById(R.id.contact_description_field);

        submitButton = findViewById(R.id.contact_submit_button);

        setSubmitEnabled(false);

        setListeners();
    }

    private void submitButtonAction(){
        String subject = subjectField.getText().toString();
        String description = descField.getText().toString();

        sendEmail(subject, description);
    }

    private void sendEmail(String subject, String description){
        String address = getResources().getString(R.string.contact_email);
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, description);

        startActivity(Intent.createChooser(intent, "Choose Email client:"));
    }

    private void setListeners(){
        submitButton.setOnClickListener(view -> {
            submitButtonAction();
        });
        subjectField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s){
                if(s.toString().isBlank())
                    if(submitButton.isEnabled()){
                        setSubmitEnabled(false);
                        return;
                    }
                if(!submitButton.isEnabled()){
                    setSubmitEnabled(true);
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
    }

    private void setSubmitEnabled(boolean b){
        submitButton.setEnabled(b);
        submitButton.setBackgroundColor(getResources().getColor(b ?
                    R.color.gruvbox_green : R.color.gruvbox_green_grayed, null));
    }

}
