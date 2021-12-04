package com.example.diemdanhhocvienandroid2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diemdanhhocvienandroid2.HomeActivity;
import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.adapter.StudentAdapter;
import com.example.diemdanhhocvienandroid2.api.ApiClient;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.Student;
import com.example.diemdanhhocvienandroid2.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentFragment extends Fragment {

     public static final String TAG = StudentFragment.class.getName();
    private View mView;
    private HomeActivity mHomeActivity;
    private RecyclerView rcv_student;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StudentAdapter studentAdapter;

    private ClassP classP;
    private User user = HomeActivity.user;
    private List<Student> studentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_student, container, false);



        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_student_of_class, container, false);
        mHomeActivity = (HomeActivity) getActivity();

        rcv_student = mView.findViewById(R.id.rcv_student);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mHomeActivity);
        rcv_student.setLayoutManager(linearLayoutManager);

        //get data from api
        getStudentInClass();

        //reload
        swipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout_student);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStudentInClass();
                studentAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        setFloatingActionButton();


        //set title toolbar
        mHomeActivity.getSupportActionBar().setTitle("Student");


        return mView;
    }


    private void setFloatingActionButton() {

        //floating action button become a menu
        View sheetView = mView.findViewById(R.id.fab_sheet);
        View overlay = mView.findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.fab_sheet_color);
        int fabColor = getResources().getColor(R.color.fab_color);
        FloatingActionButton fab = mView.findViewById(R.id.fab);

        // Initialize material sheet FAB
        MaterialSheetFab materialSheetFab = new MaterialSheetFab(fab, sheetView, overlay,
                sheetColor, fabColor);
         //show fab func student
        TextView create_student = mView.findViewById(R.id.fab_add_student);
        TextView del_multi = mView.findViewById(R.id.fab_attendance_student);
        create_student.setText("Create student");
        del_multi.setText("Delete mutiple student");

        //create student
        create_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeActivity.goToStudentCreateFagment();
            }
        });
        //del multiple student
        del_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeActivity.goToStudentDeleteMultipleFagment(studentList);
            }
        });
    }



    private  void getStudentInClass(){
        ApiClient.getStudentService().Getstudents(user.getUserId()).enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()){
                    studentList =response.body();
                    studentAdapter = new StudentAdapter(studentList);
                    rcv_student.setAdapter(studentAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.w(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }
}