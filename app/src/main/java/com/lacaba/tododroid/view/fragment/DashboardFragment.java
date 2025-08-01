package com.lacaba.tododroid.view.fragment;


import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.todo.ToDoListController;
import com.lacaba.tododroid.model.ResourceRepository;
import com.lacaba.tododroid.view.adapter.ToDoListAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DashboardFragment extends Fragment {

    private LinearLayout panel;
    private RecyclerView recycler;
    private SwipeRefreshLayout refresher;

    private ToDoListController todolistController;

    public DashboardFragment(){
        super(R.layout.fragment_dashboard);
        todolistController = new ToDoListController();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

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

        // panel = getActivity().findViewById(R.id.dashboard_content_panel);
        recycler = view.findViewById(R.id.dashboard_recyclerview);
        refresher = view.findViewById(R.id.dashboard_refresh);

        refresher.setOnRefreshListener(() -> {
            refresher.setRefreshing(false);
        });

        loadToDoLists();
    }

    private void loadToDoLists(){
        String userId = ResourceRepository.getInstance().getCurUser().getId();
        todolistController.getToDoLists(userId, todolists -> {
            recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            ToDoListAdapter adapter = new ToDoListAdapter(todolists);
            recycler.setAdapter(adapter);
        });
    }

}
