package com.example.diemdanhhocvienandroid2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diemdanhhocvienandroid2.HomeActivity;
import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

public class StudentFragment extends Fragment {

    public static final String TAG = StudentFragment.class.getName();
    private View mView;
    private HomeActivity mHomeActivity;

    private int idClass;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_student, container, false);


        Bundle bundle = getArguments();
        if(bundle!=null){
            int idClass = (int) bundle.get("idClass");
            if(idClass!=0){
                this.idClass = idClass;
                Log.w(TAG, "idClass: "+idClass );
            }
        }

        return mView;

    }
}