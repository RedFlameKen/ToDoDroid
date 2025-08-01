package com.lacaba.tododroid.view.adapter;

import com.lacaba.tododroid.model.todo.ToDoList;
import com.lacaba.tododroid.view.activity.ToDoListActivity;
import com.lacaba.tododroid.view.dialog.ToDoListItemOptionsDialog;
import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.DashboardController;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListItemViewHolder> {

    private DashboardController controller;
    private FragmentManager fragman;

    public ToDoListAdapter(DashboardController controller, FragmentManager fragman){
        this.controller = controller;
        this.fragman = fragman;
    }

    @NonNull
    @Override
    public ToDoListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_todolist_item, parent, false);
        return new ToDoListItemViewHolder(view, controller, fragman);
    }

    @Override
    public int getItemCount() {
        return controller.getTodolists().size();
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListItemViewHolder holder, int position) {
        ToDoList todolist = controller.getTodolists().get(position);
        holder.setToDoList(todolist);

        holder.titleLabel.setText(todolist.getName());
    }

    public static class ToDoListItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titleLabel;
        private ToDoList todolist;
        private DashboardController controller;
        private FragmentManager fragman;

        public ToDoListItemViewHolder(@NonNull View itemView, DashboardController controller, FragmentManager fragman) {
            super(itemView);
            this.controller = controller;
            this.fragman = fragman;

            titleLabel = itemView.findViewById(R.id.todolistitem_title_label);

            itemView.setOnClickListener(view -> {
                showTodoListActivity(itemView);
            });

            itemView.setOnLongClickListener(view -> {
                ToDoListItemOptionsDialog dialog = new ToDoListItemOptionsDialog(todolist, controller);
                dialog.show(fragman, ToDoListItemOptionsDialog.TAG);
                return true;
            });
        }

        private void setToDoList(ToDoList todolist){
            this.todolist = todolist;
        }

        private void showTodoListActivity(View view){
            Intent intent = new Intent(view.getContext(), ToDoListActivity.class);
            intent.putExtra("todolist_id", todolist.getId());
            view.getContext().startActivity(intent);
        }


    }
    
}
