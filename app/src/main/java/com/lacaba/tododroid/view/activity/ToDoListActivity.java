package com.lacaba.tododroid.view.activity;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.todo.ToDoListController;
import com.lacaba.tododroid.controller.ToDoListViewController;
import com.lacaba.tododroid.model.todo.BoardType;
import com.lacaba.tododroid.view.adapter.ToDoBoardAdapter;
import com.lacaba.tododroid.view.fragment.TodoBoardFragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class ToDoListActivity extends AppCompatActivity {

    private static final int PAGE_COUNT = 3;

    private TextView headerLabel;

    private TabLayout tabs;
    private ViewPager2 pager;
    private FragmentStateAdapter pagerAdapter;

    private ToDoListViewController controller;
    private ToDoListController todoListController;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);
        initComponents();
    }

    private void initComponents() {
        headerLabel = findViewById(R.id.todolist_header_label);
        tabs = findViewById(R.id.todolist_tabs);
        pager = findViewById(R.id.todolist_content_pager);

        todoListController = new ToDoListController();

        String todolistId;
        if((todolistId = getIntent().getStringExtra("todolist_id")) != null){
            todoListController.findToDoListById(todolistId, todolist -> {
                assert todolist != null : "todolist should not be null if todolistId does exist";
                controller = ToDoListViewController.getInstance();
                controller.initController(todolist, tabs);
                initToDoBoardAdapter(BoardType.TODO);
                initToDoBoardAdapter(BoardType.DOING);
                initToDoBoardAdapter(BoardType.DONE);
                pagerAdapter = new ScreenSlideAdapter(this, controller);
                headerLabel.setText(todolist.getName());
                pager.setAdapter(pagerAdapter);
                initTabs();
                tabs.getTabAt(1).select();
            });
        }

    }

    @Override
    public void onConfigurationChanged(Configuration config){
        super.onConfigurationChanged(config);

        controller.notifyAdapterState();
    }

    private void initToDoBoardAdapter(BoardType type){
        ToDoBoardAdapter adapter = new ToDoBoardAdapter(controller, type, getSupportFragmentManager());
        controller.setBoardAdapter(type, adapter);
    }

    private void initTabs(){
        new TabLayoutMediator(tabs, pager, 
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("ToDo");
                            break;
                        case 1:
                            tab.setText("Doing");
                            break;
                        case 2:
                            tab.setText("Done");
                            break;
                        default:
                            break;
                    }
                }).attach();
    }
    
    private class ScreenSlideAdapter extends FragmentStateAdapter {

        private ToDoListViewController controller;

        public ScreenSlideAdapter(FragmentActivity fa, ToDoListViewController controller){
            super(fa);
            this.controller = controller;
        }

        @Override
        public Fragment createFragment(int position){
            TodoBoardFragment frag = null;
            Bundle args = new Bundle();
            switch (position) {
                case 0:
                    args.putString("board_type", BoardType.TODO.toString());
                    break;
                case 1:
                    args.putString("board_type", BoardType.DOING.toString());
                    break;
                case 2:
                    args.putString("board_type", BoardType.DONE.toString());
                    break;
            }
            frag = new TodoBoardFragment();
            frag.setArguments(args);
            return frag;
        }

        @Override
        public int getItemCount(){
            return PAGE_COUNT;
        }

    }

}
