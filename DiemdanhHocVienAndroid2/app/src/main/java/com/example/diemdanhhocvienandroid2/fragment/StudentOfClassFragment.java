package com.example.diemdanhhocvienandroid2.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.diemdanhhocvienandroid2.adapter.StudentAdapter;
import com.example.diemdanhhocvienandroid2.api.ApiClient;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.Student;
import com.example.diemdanhhocvienandroid2.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    private ClassP classP;
    private User user = HomeActivity.user;
    private List<Student> studentList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle = getArguments();
        if(bundle!=null){
            ClassP classP = (ClassP) bundle.get("object_class");
            if(classP!=null){
                 this.classP = classP;
            }
        }
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_student_of_class, container, false);
        mHomeActivity = (HomeActivity) getActivity();

        rcv_student = mView.findViewById(R.id.rcv_student);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mHomeActivity);
        rcv_student.setLayoutManager(linearLayoutManager);

        //get data from api
        getStudentInClass();

        return mView;

    }

    @Override
    public void onStart() {
        super.onStart();

        //show fab func student
        TextView fab_1 = mHomeActivity.findViewById(R.id.fab_add_student);
        TextView fab_2 = mHomeActivity.findViewById(R.id.fab_attendance_student);
        fab_1.setVisibility(View.VISIBLE);
        fab_2.setVisibility(View.VISIBLE);

        //
        fab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeActivity.goToAttendanceStudent(classP);
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        //hide func in fab
        TextView fab_1 = mHomeActivity.findViewById(R.id.fab_add_student);
        TextView fab_2 = mHomeActivity.findViewById(R.id.fab_attendance_student);
         fab_1.setVisibility(View.GONE);
        fab_2.setVisibility(View.GONE);
     }


    private  void getStudentInClass(){
        ApiClient.getStudentService().StudentInClass(classP.getId()).enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()){
                    Log.w(TAG, "onResponse: isSuccessful");
                    Log.w(TAG, "response.bofy.size()"+response.body().size());

                    studentList =response.body();
                    StudentAdapter studentAdapter = new StudentAdapter(studentList);
                    rcv_student.setAdapter(studentAdapter);

                    Log.w(TAG, "studentList.size: "+studentList.size() );
                    Log.w(TAG, "classP.getId: "+classP.getId() );
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(mHomeActivity.getApplicationContext(), "onFailure", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}