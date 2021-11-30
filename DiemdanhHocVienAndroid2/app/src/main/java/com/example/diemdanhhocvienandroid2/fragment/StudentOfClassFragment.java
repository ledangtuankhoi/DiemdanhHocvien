package com.example.diemdanhhocvienandroid2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.models.ClassP;

public class StudentOfClassFragment extends Fragment {

    public static final String TAG = StudentOfClassFragment.class.getName();
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_student_of_class, container, false);


        Bundle bundle = getArguments();
        if(bundle!=null){
            ClassP classP = (ClassP) bundle.get("object_class");
            if(classP!=null){
                TextView tv_temp = mView.findViewById(R.id.tv_temp);
                tv_temp.setText(classP.toString());
            }
        }

        return mView;

    }
}