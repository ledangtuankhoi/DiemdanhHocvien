package com.example.diemdanhhocvienandroid2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.diemdanhhocvienandroid2.HomeActivity;
import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.adapter.StudentDelMultipleAdapter;
import com.example.diemdanhhocvienandroid2.adapter.StudentRemoveMultipleInClassAdapter;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.Student;
import com.example.diemdanhhocvienandroid2.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

import java.util.ArrayList;
import java.util.List;

public class StudentRemoveMultipleInClassFragment extends Fragment {

    public static final String TAG = StudentRemoveMultipleInClassFragment.class.getName();
    private View mView;
    private HomeActivity mHomeActivity;
    private RecyclerView rcv_student;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StudentRemoveMultipleInClassAdapter studentRemoveMultipleInClassAdapter;

    private ClassP classP;
    private User user = HomeActivity.user;
    private List<Student> studentList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle = getArguments();
        if (bundle != null) {
            List<Student> studentList = (List<Student>) bundle.get("List_object_student");
             if (studentList != null) {
                this.studentList = studentList;
                Log.w(TAG, "onCreateView: "+this.studentList.size() );
            }
        }
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_student_of_class, container, false);
        mHomeActivity = (HomeActivity) getActivity();

        //set title toolbar
        mHomeActivity.getSupportActionBar().setTitle("Remove multiple student ");

        //init recyclerveiew
        rcv_student = mView.findViewById(R.id.rcv_student);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mHomeActivity);
        rcv_student.setLayoutManager(linearLayoutManager);
        //load adapter
        studentRemoveMultipleInClassAdapter = new StudentRemoveMultipleInClassAdapter(mHomeActivity,studentList);
        rcv_student.setAdapter(studentRemoveMultipleInClassAdapter);

        //reload
        swipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout_student);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                getStudent();
                studentRemoveMultipleInClassAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        setFloatingActionButton();


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