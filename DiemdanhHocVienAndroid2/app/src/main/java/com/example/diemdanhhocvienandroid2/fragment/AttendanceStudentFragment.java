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
    private HomeActivity mHomeActivity;
    private RecyclerView rcv_attendance_student;

    private ClassP classP;
    private User user = HomeActivity.user;
    private List<AttendanceStudent> attendanceStudentList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //recevie data form bundel studentOfClassFragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            ClassP classP = (ClassP) bundle.get("object_class");
            if (classP != null) {
                this.classP = classP;
            }
        }


        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_attendance_student, container, false);


        rcv_attendance_student = mView.findViewById(R.id.rcv_attendance_student);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mHomeActivity);
        rcv_attendance_student.setLayoutManager(linearLayoutManager);

        //get data from api
        getStudentInClass();



        return mView;

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void getStudentInClass() {
        ApiClient.getStudentService().AttendanceStudent(classP.getId()).enqueue(new Callback<List<AttendanceStudent>>() {
            @Override
            public void onResponse(Call<List<AttendanceStudent>> call, Response<List<AttendanceStudent>> response) {
                if(response.isSuccessful()){
                    attendanceStudentList = response.body();
                     Log.w(TAG, "isSuccessful: "+attendanceStudentList.size() );

                    AttendanceStudentAdapter attendanceStudentAdapter = new AttendanceStudentAdapter(attendanceStudentList);
                    rcv_attendance_student.setAdapter(attendanceStudentAdapter);


                }
            }

            @Override
            public void onFailure(Call<List<AttendanceStudent>> call, Throwable t) {
                Toast.makeText(mHomeActivity.getApplicationContext(), "onFailure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}