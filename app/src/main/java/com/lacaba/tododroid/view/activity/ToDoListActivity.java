package com.lacaba.tododroid.view.activity;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.view.component.scroll.SnapScrollH;

import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ToDoListActivity extends AppCompatActivity {

    private LinearLayout panel;
    private HorizontalScrollView scrollView;
    private TextView headerLabel;
    private SnapScrollH snapScroll;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_todolist);

        initComponents();
    }

    private void initComponents() {
        panel = findViewById(R.id.todolist_content_panel);
        scrollView = findViewById(R.id.todolist_content_scroll);
        headerLabel = findViewById(R.id.todolist_header_label);

        String name;
        if((name = getIntent().getStringExtra("ws_name")) != null){
            headerLabel.setText(name);
        }

        snapScroll = new SnapScrollH(scrollView);

    }
    
}
