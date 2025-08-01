package com.lacaba.tododroid.view.adapter;

import com.lacaba.tododroid.model.todo.BoardType;
import com.lacaba.tododroid.model.todo.ToDo;
import com.lacaba.tododroid.view.dialog.ExpandToDoDialog;
import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.ToDoListViewController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoBoardAdapter extends RecyclerView.Adapter<ToDoBoardAdapter.ToDoBoardItemHolder>{

    private ToDoListViewController controller;
    private BoardType type;
    private FragmentManager fragman;

    public ToDoBoardAdapter(ToDoListViewController controller, BoardType type, FragmentManager fragman){
        this.controller = controller;
        this.type = type;
        this.fragman = fragman;
    }

    @NonNull
    @Override
    public ToDoBoardItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_todoboard_item_panel, parent, false);
        return new ToDoBoardItemHolder(view, fragman, controller, type);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoBoardItemHolder holder, int position) {
        ToDo todo = controller.getBoardArray(type).get(position);
        holder.setToDo(todo);

        holder.textView.setText(todo.getTitle());
        holder.checkBox.setChecked(todo.isDone());
        holder.initCheckBoxEvent();
    }

    @Override
    public int getItemCount() {
        return controller.getBoardArray(type).size();
    }

    public static class ToDoBoardItemHolder extends RecyclerView.ViewHolder {

        private ToDo todo;
        private TextView textView;
        private CheckBox checkBox;

        private ToDoListViewController controller;

        public ToDoBoardItemHolder(View itemView, FragmentManager fragman, ToDoListViewController controller, BoardType type) {
            super(itemView);
            this.controller = controller;
            textView = itemView.findViewById(R.id.todoitem_title_label);
            checkBox = itemView.findViewById(R.id.todoitem_checkbox);

            itemView.setOnClickListener(view -> {
                ExpandToDoDialog dialog = new ExpandToDoDialog(todo, controller, type);
                dialog.show(fragman, ExpandToDoDialog.TAG);
            });

        }

        private void setToDo(ToDo todo){
            this.todo = todo;
        }

        private void initCheckBoxEvent(){
            checkBox.post(() -> {
                checkBox.setOnCheckedChangeListener((view, isChecked) -> {
                    todo.setDone(isChecked);
                    controller.updateToDoNoNotify(todo);
                });
            });
        }

    }
    
}
