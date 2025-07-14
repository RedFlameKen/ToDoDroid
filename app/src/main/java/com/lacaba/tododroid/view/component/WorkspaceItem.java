package com.lacaba.tododroid.view.component;

import com.lacaba.tododroid.R;

import com.lacaba.tododroid.model.Workspace;
import com.lacaba.tododroid.view.activity.ToDoListActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class WorkspaceItem extends ConstraintLayout {

    private Workspace workspace;
    private TextView nameLabel;

    public WorkspaceItem(Context context, Workspace workspace){
        super(context);
        this.workspace = workspace;
        initComponents();
    }

    private void initComponents(){
        LayoutParams ownParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
                );
        setLayoutParams(ownParams);
        setBackgroundColor(getContext().getColor(R.color.gruvbox_black));
        nameLabel = new TextView(getContext());
        nameLabel.setId(generateViewId());
        nameLabel.setText(workspace != null ? workspace.getName() : "Undefined");
        nameLabel.setTextColor(getContext().getColor(R.color.gruvbox_white_light));
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
                );
        nameLabel.setLayoutParams(params);
        ConstraintSet set = new ConstraintSet();
        addView(nameLabel);
        set.clone(this);
        set.connect(nameLabel.getId(), ConstraintSet.TOP, this.getId(), ConstraintSet.TOP);
        set.connect(nameLabel.getId(), ConstraintSet.START, this.getId(), ConstraintSet.START);
        set.connect(nameLabel.getId(), ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM);
        set.applyTo(this);

    }

}
