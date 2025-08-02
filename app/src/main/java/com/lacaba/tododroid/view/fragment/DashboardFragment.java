package com.lacaba.tododroid.view.fragment;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.DashboardController;
import com.lacaba.tododroid.view.adapter.ToDoListAdapter;

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

    private DashboardController dashboardController;

    public DashboardFragment(DashboardController dashboardController){
        super(R.layout.fragment_dashboard);
        this.dashboardController = dashboardController;
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

        recycler = view.findViewById(R.id.dashboard_recyclerview);
        refresher = view.findViewById(R.id.dashboard_refresh);

        refresher.setOnRefreshListener(() -> {
            dashboardController.reloadToDoLists(() -> refresher.setRefreshing(false));
        });

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ToDoListAdapter adapter = new ToDoListAdapter(dashboardController, getParentFragmentManager());

        dashboardController.setTodolistAdapter(adapter);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        dashboardController.reloadToDoLists(null);
    }

}
