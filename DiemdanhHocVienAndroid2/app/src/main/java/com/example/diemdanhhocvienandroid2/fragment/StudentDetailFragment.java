package com.example.diemdanhhocvienandroid2.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diemdanhhocvienandroid2.HomeActivity;
import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.api.ApiClient;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.Student;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDetailFragment extends Fragment {

    public static final String TAG = StudentDetailFragment.class.getName();
    private View mView;
    private TextInputLayout email, numberphone, brithday, lastname, firstname, holyname;
    private Button btn_phone, btn_email;
    private ImageView img;
    private HomeActivity mHomeActivity;

    //curent student process
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
                Log.w(TAG, "onCreateView: " + String.valueOf(student.getId()));
            }
        }

        //
        setHasOptionsMenu(true);

        //set view fragment detail
        setView();

        //call phone and send email
        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = numberphone.getEditText().getText().toString();
                if (phone.isEmpty()) {
                    Toast.makeText(mView.getContext(), "not phone number", Toast.LENGTH_SHORT).show();
                } else {
                    String s = "tel:" + phone;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(s));
                    startActivity(intent);
                }
            }
        });
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String mailto = "mailto:useremail@gmail.com" +
//                        "?cc=" +
//                        "&subject=" + Uri.encode("your subject") +
//                        "&body=" + Uri.encode("your mail body");
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//                emailIntent.setData(Uri.parse(mailto));
//
//                try {
//                    startActivity(emailIntent);
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(mView.getContext(), "Error to open email app", Toast.LENGTH_SHORT).show();
//                }

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT, "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(mView.getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return mView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_crud_student, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_student_edit:

                StudentEditFragment studentEditFragment = new StudentEditFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_student", (Serializable) student);
                studentEditFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, studentEditFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;

            case R.id.action_student_delete:
                ApiClient.getStudentService().DeleteStudent(student.getId()).enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        Toast.makeText(mView.getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
//                        Toast.makeText(mView.getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "onFailure: " + t.getMessage());
                    }
                });
                break;
        }
        return true;
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