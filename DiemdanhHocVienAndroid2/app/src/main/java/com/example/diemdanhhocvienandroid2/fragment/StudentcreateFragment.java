package com.example.diemdanhhocvienandroid2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.diemdanhhocvienandroid2.HomeActivity;
import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.api.ApiClient;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.Student;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentcreateFragment extends Fragment {

    public static final String TAG = StudentcreateFragment.class.getName();

    private View mView;
    private HomeActivity mHomeActivity;
    TextInputLayout email,numberphone,brithday,lastname,firstname,holyname;

    private ClassP classP;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_student_create, container, false);


        Bundle bundle = getArguments();
        if (bundle != null) {
            ClassP s = (ClassP) bundle.get("object_class");
             if (s!=null) {
                this.classP = s;
            }
        }


        Button btn_create = mView.findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(TAG, "onClick: " );
                CreateStudent();
                getParentFragmentManager().popBackStack();
            }
        });

        return mView;

    }

    private void CreateStudent( ) {
        holyname = mView.findViewById(R.id.ed_holyname);
        firstname = mView.findViewById(R.id.ed_firstname);
        lastname = mView.findViewById(R.id.ed_lastname);
        brithday = mView.findViewById(R.id.ed_brithday);
        numberphone = mView.findViewById(R.id.ed_numberphone);
        email = mView.findViewById(R.id.ed_email);

        Student s = new Student();
        s.setHolyName(holyname.getEditText().getText().toString());
        s.setFirstName(firstname.getEditText().getText().toString());
        s.setLastName(lastname.getEditText().getText().toString());
        s.setBOD(brithday.getEditText().getText().toString());
        s.setNumPhone(numberphone.getEditText().getText().toString());
        s.setEmail(email.getEditText().getText().toString());

        ApiClient.getStudentService().createstudent(s).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
                    Log.w(TAG, "onResponse: " + response.body().getHolyName());
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.w(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }


}