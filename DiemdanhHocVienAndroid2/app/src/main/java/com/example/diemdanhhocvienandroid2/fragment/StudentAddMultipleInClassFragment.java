package com.example.diemdanhhocvienandroid2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.MenuRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.diemdanhhocvienandroid2.HomeActivity;
import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.adapter.StudentAddMultipleInClassAdapter;
import com.example.diemdanhhocvienandroid2.adapter.StudentRemoveMultipleInClassAdapter;
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

public class StudentAddMultipleInClassFragment extends Fragment {

    public static final String TAG = StudentAddMultipleInClassFragment.class.getName();
    private View mView;
    private HomeActivity mHomeActivity;
    private RecyclerView rcv_student;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StudentAddMultipleInClassAdapter studentAddMultipleInClassAdapter;

    private ClassP classP;
    private User user = HomeActivity.user;
    private List<Student> studentList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//
        Bundle bundle = getArguments();
        if (bundle != null) {
            ClassP a = (ClassP) bundle.get("object_class");
            if (a != null) {
                this.classP =a;

            }
        }

        GetstudentsNullClass();
        
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_student_of_class, container, false);
        mHomeActivity = (HomeActivity) getActivity();

        //set title toolbar
        mHomeActivity.getSupportActionBar().setTitle("Add student to class ");

        //init recyclerveiew
        rcv_student = mView.findViewById(R.id.rcv_student);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mHomeActivity);
        rcv_student.setLayoutManager(linearLayoutManager);


        //set floating buttun action
        setFloatingActionButton();

        //reload
        swipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout_student);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetstudentsNullClass();
                studentAddMultipleInClassAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return mView;
    }

    private void GetstudentsNullClass() {
        ApiClient.getStudentService().GetstudentsNullClass(user.getUserId()).enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                studentList = response.body();
                Log.w(TAG, "onResponse:     "+response.body().size()+" ="+studentList.size());

                //load adapter
                studentAddMultipleInClassAdapter = new StudentAddMultipleInClassAdapter(mHomeActivity, studentList, classP, new StudentAddMultipleInClassAdapter.IFinishListener() {
                    @Override
                    public void onFinish() {
                        getFragmentManager().popBackStack();
                    }
                });
                rcv_student.setAdapter(studentAddMultipleInClassAdapter);
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.w(TAG, "onFailure: "+t.getMessage() );
            }
        });
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
        TextView fab_1 = mView.findViewById(R.id.fab_add_student);
        TextView fab_2 = mView.findViewById(R.id.fab_attendance_student);

        //attendance student
        fab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        //add student
        fab_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        fab.hide();
    }

}