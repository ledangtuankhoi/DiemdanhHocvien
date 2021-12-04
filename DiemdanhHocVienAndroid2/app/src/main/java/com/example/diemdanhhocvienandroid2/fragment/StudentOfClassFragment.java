package com.example.diemdanhhocvienandroid2.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.MockView;
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
import android.widget.Toast;

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
import java.util.PrimitiveIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentOfClassFragment extends Fragment {

    public static final String TAG = StudentOfClassFragment.class.getName();
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


        Bundle bundle = getArguments();
        if (bundle != null) {
            ClassP classP = (ClassP) bundle.get("object_class");
            if (classP != null) {
                this.classP = classP;
            }
        }
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_student_of_class, container, false);
        mHomeActivity = (HomeActivity) getActivity();

        rcv_student = mView.findViewById(R.id.rcv_student);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mHomeActivity);
        rcv_student.setLayoutManager(linearLayoutManager);

        //get data from api
        getStudent();

        //reload
        swipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout_student);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStudent();
                studentAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        setFloatingActionButton();


        //set title toolbar
        mHomeActivity.getSupportActionBar().setTitle("student in class");

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
//        fab.hide();
        //show fab func student
        TextView fab_1 = mView.findViewById(R.id.fab_add_student);
        TextView fab_2 = mView.findViewById(R.id.fab_attendance_student);

        //attendance student
        fab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeActivity.goToAttendanceStudent(classP);
            }
        });
        //add student
        fab_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
            }
        });
    }

    private  void getStudent(){
        ApiClient.getStudentService().StudentInClass(classP.getId()).enqueue(new Callback<List<Student>>() {
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