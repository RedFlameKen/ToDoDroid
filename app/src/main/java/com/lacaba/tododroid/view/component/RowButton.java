package com.lacaba.tododroid.view.component;

import com.lacaba.tododroid.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RowButton extends FrameLayout {

    private ImageView icon;
    private TextView label;

    public RowButton(@NonNull Context context) {
        super(context);
    }

    public RowButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RowButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RowButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onFinishInflate(){
        super.onFinishInflate();

        icon = findViewById(R.id.rowbutton_icon);
        label = findViewById(R.id.rowbutton_label);
    }

    private void loadData(int drawableId, String label){
        icon.setImageDrawable(getResources().getDrawable(drawableId, null));
        this.label.setText(label);
    }

    public static RowButton inflate(LayoutInflater inflater, int drawableId, String label){
        RowButton button = (RowButton) inflater.inflate(R.layout.layout_row_button, null, false);
        button.loadData(drawableId, label);
        return button;
    }

}
