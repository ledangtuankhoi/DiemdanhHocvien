package com.example.diemdanhhocvienandroid2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.api.ApiClient;
import com.example.diemdanhhocvienandroid2.models.Student;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentEditFragment extends Fragment {


    public static final String TAG = StudentDetailFragment.class.getName();
    private View mView;
    private TextInputLayout email,numberphone,brithday,lastname,firstname,holyname;
    Button btn_save, btn_cancel;
    private ImageView img;


    //curent student process
    private Student student;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_student_edit, container, false);



        //get data from class of student and student
        Bundle bundle = getArguments();
        if (bundle != null) {
            Student student = (Student) bundle.get("object_student");
            if (student != null) {
                this.student = student;
                Log.w(TAG, "onCreateView: "+student.toString() );
            }
        }

        //set view
        setView();

        //action cancel
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
        //action save
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                student.setEmail(email.getEditText().getText().toString());
                student.setNumPhone(numberphone.getEditText().getText().toString());
                student.setBOD(brithday.getEditText().getText().toString());
                student.setLastName(lastname.getEditText().getText().toString());
                student.setFirstName(firstname.getEditText().getText().toString());
                student.setHolyName(holyname.getEditText().getText().toString());

                ApiClient.getStudentService().PutStudent(student.getId(),student).enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        Toast.makeText(mView.getContext(), "Save Success", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Log.w(TAG, "onFailure: "+t.getMessage() );
                    }
                });
            }
        });


        return mView;
    }
    



    private void setView() {
        holyname = mView.findViewById(R.id.ed_holyname);
        firstname = mView.findViewById(R.id.ed_firstname);
        lastname = mView.findViewById(R.id.ed_lastname);
        brithday = mView.findViewById(R.id.ed_brithday);
        numberphone = mView.findViewById(R.id.ed_numberphone);
        email = mView.findViewById(R.id.ed_email);

        btn_cancel = mView.findViewById(R.id.btn_cancel);
        btn_save = mView.findViewById(R.id.btn_save);

        holyname.getEditText().setText(student.getHolyName());
        firstname.getEditText().setText(student.getFirstName());
        lastname.getEditText().setText(student.getLastName());
        brithday.getEditText().setText(student.getBOD());
        numberphone.getEditText().setText(student.getNumPhone());
        email.getEditText().setText(student.getEmail());
    }
}