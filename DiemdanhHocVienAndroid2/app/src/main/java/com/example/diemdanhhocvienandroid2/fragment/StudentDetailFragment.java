package com.example.diemdanhhocvienandroid2.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.Student;
import com.google.android.material.textfield.TextInputLayout;

public class StudentDetailFragment extends Fragment {

    public static final String TAG = StudentDetailFragment.class.getName();
    private View mView;
    private TextInputLayout email,numberphone,brithday,lastname,firstname,holyname;
    Button btn_phone, btn_email;
    private ImageView img;

    private Student student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_student_detail, container, false);



        //get data from class of student and student
        Bundle bundle = getArguments();
        if (bundle != null) {
            Student student = (Student) bundle.get("object_student");
            if (student != null) {
                this.student = student;
                Log.w(TAG, "onCreateView: "+String.valueOf(student.getId()) );
            }
        }

        //set view fragment detail
        setView();

        //call phone and send email
        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = numberphone.getEditText().getText().toString();
                if(phone.isEmpty()){
                    Toast.makeText(mView.getContext(), "not phone number", Toast.LENGTH_SHORT).show();
                }else{
                    String s = "tel:"+phone;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(s));
                    startActivity(intent);
                }
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

        btn_phone = mView.findViewById(R.id.btn_phone);
        btn_email = mView.findViewById(R.id.btn_email);
        
        holyname.getEditText().setText(student.getHolyName());
        firstname.getEditText().setText(student.getFirstName());
        lastname.getEditText().setText(student.getLastName());
        brithday.getEditText().setText(student.getBOD());
        numberphone.getEditText().setText(student.getNumPhone());
        email.getEditText().setText(student.getEmail());
    }
}