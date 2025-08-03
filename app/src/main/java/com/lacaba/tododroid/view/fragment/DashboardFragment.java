package com.lacaba.tododroid.view.fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.DashboardController;
import com.lacaba.tododroid.view.adapter.ToDoListAdapter;
import com.lacaba.tododroid.view.dialog.CreateToDoListDialog;
import com.lacaba.tododroid.view.dialog.PromptDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DashboardFragment extends Fragment {

    public static final String TAG = "DashboardFragment";

    private LinearLayout panel;
    private RecyclerView recycler;
    private SwipeRefreshLayout refresher;
    private FloatingActionButton addButton;

    private DashboardController dashboardController;

    public DashboardFragment(){
        super(R.layout.fragment_dashboard);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        if(panel != null){
            if(panel.getChildCount() > 0){
                while(panel.getChildCount() > 0){
                    panel.removeView(panel.getChildAt(0));
                }
            }
        }

        addButton = view.findViewById(R.id.dashboard_add_button);
        addButton.setOnClickListener(v -> {
            showCreateToDoListDialog();
        });

        recycler = view.findViewById(R.id.dashboard_recyclerview);
        refresher = view.findViewById(R.id.dashboard_refresh);

        dashboardController = DashboardController.getInstance();

        refresher.setOnRefreshListener(() -> {
            dashboardController.reloadToDoLists(() -> refresher.setRefreshing(false));
        });

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        dashboardController.loadToDoList(controller -> {
            controller.setTodolistAdapter(new ToDoListAdapter(controller, getParentFragmentManager()));
            recycler.setAdapter(controller.getTodolistAdapter());
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        dashboardController.reloadToDoLists(null);
    }

    private void showCreateToDoListDialog(){
        PromptDialog dialog = new PromptDialog.Builder()
            .setOkText("Create")
            .setHintText("ToDoList name")
            .setHeaderText("Create ToDoList")
            .setOnOkListener(text -> dashboardController.addToDoList(text))
            .build();
        dialog.show(getParentFragmentManager(), CreateToDoListDialog.TAG);
    }

}
