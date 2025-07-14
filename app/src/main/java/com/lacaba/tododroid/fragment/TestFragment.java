package com.lacaba.tododroid.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.lacaba.tododroid.R;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.TextView;

public class TestFragment extends Fragment {

    private TextView textView;
    private String text;
    private int backgroundColor;

    public TestFragment(){
        super(R.layout.fragment_test);
        text = "balls";
        backgroundColor = R.color.white;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    // @Override
    // public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    //     return inflater.inflate(R.layout.fragment_test, container, false);
    // }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
        setExitTransition(inflater.inflateTransition(R.transition.slide_left));
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        TextView textView = view.findViewById(R.id.fragment_test_text);
        // Intent intent = getActivity().getIntent().getBun
        textView.setText(this.text);
        this.getView().setBackgroundColor(getResources().getColor(backgroundColor, null));
    }
    
}
