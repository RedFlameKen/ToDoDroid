package com.lacaba.tododroid.view.fragment;

import java.util.ArrayList;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.model.Workspace;
import com.lacaba.tododroid.view.component.WorkspaceItem;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {

    private ArrayList<WorkspaceItem> workspaceItems;
    private LinearLayout panel;

    public DashboardFragment(){
        super(R.layout.fragment_dashboard);
        this.workspaceItems = new ArrayList<>();
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
        panel = getActivity().findViewById(R.id.dashboard_content_panel);

        for (int i = 0; i < 5; i++) {
            Workspace ws = new Workspace.Builder()
                .name(new StringBuilder().append("workspace ").append(i).toString())
                .build();
            workspaceItems.add(new WorkspaceItem(requireContext(), ws));
            panel.addView(workspaceItems.get(i));
        }

    }

}
