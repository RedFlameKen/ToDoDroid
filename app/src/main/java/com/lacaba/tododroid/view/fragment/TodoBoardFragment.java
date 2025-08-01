package com.lacaba.tododroid.view.fragment;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.ToDoListViewController;
import com.lacaba.tododroid.model.todo.BoardType;
import com.lacaba.tododroid.model.todo.ToDo;
import com.lacaba.tododroid.view.component.NewToDoInput;
import com.lacaba.tododroid.view.component.RowButton;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TodoBoardFragment extends Fragment {

    private RecyclerView recycler;
    private BoardType boardType;
    private ToDoListViewController controller;
    private CardView addTodoCardview;
    private RowButton addTodoButton;
    private LinearLayout boardPanel;

    public TodoBoardFragment(ToDoListViewController controller, BoardType boardType){
        this.controller = controller;
        this.boardType = boardType;
    }

    private ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {

        @Override
        public void onChildDraw(Canvas c, RecyclerView recycler, RecyclerView.ViewHolder holder, float x, float y, int actionState, boolean isCurrentlyActive){
            super.onChildDraw(c, recycler, holder, x, y, actionState, isCurrentlyActive);
        }

        @Override
        public boolean onMove(RecyclerView recycler, RecyclerView.ViewHolder holder, RecyclerView.ViewHolder target){
            int from = holder.getAdapterPosition();
            int to = target.getAdapterPosition();
            controller.moveToDo(boardType, from, to);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder holder, int direction){
        }

    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.layout_todo_board_panel, container, false);

        recycler = layout.findViewById(R.id.todoboard_content_panel);
        boardPanel = layout.findViewById(R.id.todoboard_board_panel);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(controller.getBoardAdapter(boardType));

        new ItemTouchHelper(callback).attachToRecyclerView(recycler);

        inputField = NewToDoInput.inflate(getLayoutInflater());
        inputField.setOnOkListener(title -> {
            addTodoFunction(title);
        });

        addTodoCardview = layout.findViewById(R.id.todoboard_add_todo_cardview);
        addTodoButton = RowButton.inflate(inflater, R.drawable.outline_add_24, "Add ToDo");
        addTodoButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addTodoButton.setClickable(true);
        addTodoButton.setOnClickListener(view -> addTodoAction());

        addTodoCardview.addView(addTodoButton);

        return layout;

    }

    private NewToDoInput inputField;

    private void addTodoAction(){
        boardPanel.removeView(addTodoCardview);
        boardPanel.addView(inputField);
        inputField.requestFocus();
    }

    private void addTodoFunction(String title){
        if(title.isBlank())
            return;
        ToDo todo = new ToDo.Builder()
            .title(title)
            .isDone(false)
            .boardType(boardType)
            .build();
        controller.addToDo(boardType, todo);
        boardPanel.removeView(inputField);
        boardPanel.addView(addTodoCardview);
        recycler.post(() -> recycler.scrollToPosition(recycler.getChildCount()));
    }

}
