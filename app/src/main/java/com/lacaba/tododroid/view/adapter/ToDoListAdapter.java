package com.lacaba.tododroid.view.adapter;

import java.util.ArrayList;

import com.lacaba.tododroid.model.todo.ToDoList;
import com.lacaba.tododroid.view.activity.ToDoListActivity;
import com.lacaba.tododroid.R;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListItemViewHolder> {

    private ArrayList<ToDoList> todolists;

    public ToDoListAdapter(ArrayList<ToDoList> todolists){
        this.todolists = todolists;
    }

    @NonNull
    @Override
    public ToDoListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_todolist_item, parent, false);
        return new ToDoListItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return todolists.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListItemViewHolder holder, int position) {
        ToDoList todolist = todolists.get(position);
        holder.setToDoList(todolist);

        holder.titleLabel.setText(todolist.getName());
    }

    public static class ToDoListItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titleLabel;
        private ToDoList todolists;

        public ToDoListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            titleLabel = itemView.findViewById(R.id.todolistitem_title_label);

            itemView.setOnClickListener(view -> {
                showTodoListActivity(itemView);
            });
        }

        private void setToDoList(ToDoList todolists){
            this.todolists = todolists;
        }

        private void showTodoListActivity(View view){
            Intent intent = new Intent(view.getContext(), ToDoListActivity.class);
            intent.putExtra("todolist_id", todolists.getId());
            view.getContext().startActivity(intent);
        }


    }
    
}
