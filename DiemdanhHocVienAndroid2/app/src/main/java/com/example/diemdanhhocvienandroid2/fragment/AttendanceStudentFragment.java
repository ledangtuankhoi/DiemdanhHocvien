package com.example.diemdanhhocvienandroid2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diemdanhhocvienandroid2.HomeActivity;
import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.adapter.AttendanceStudentAdapter;
import com.example.diemdanhhocvienandroid2.adapter.StudentAdapter;
import com.example.diemdanhhocvienandroid2.api.ApiClient;
import com.example.diemdanhhocvienandroid2.models.AttendanceStudent;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.Student;
import com.example.diemdanhhocvienandroid2.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceStudentFragment extends Fragment {

    public static final String TAG = AttendanceStudentFragment.class.getName();

    private View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_attendance_student, container, false);




        return mView;

    }

    @Override
    public void onStop() {
        super.onStop();

    }

}