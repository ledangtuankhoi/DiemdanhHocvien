package com.example.diemdanhhocvienandroid2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diemdanhhocvienandroid2.adapter.AttendanceStudentAdapter;
import com.example.diemdanhhocvienandroid2.api.ApiClient;
import com.example.diemdanhhocvienandroid2.models.AttendanceStudent;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceStudentActivity extends AppCompatActivity {

    public static final String TAG = AttendanceStudentActivity.class.getName();

    private RecyclerView recyclerView ;
    private TextView tv_empty;
    private List<AttendanceStudent> arrayList = new ArrayList<>();
    AttendanceStudentAdapter mainAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ClassP classP;
    private User user = HomeActivity.user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_student);

        //recevie data form bundel studentOfClassFragment
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            ClassP classP = (ClassP) bundle.get("object_class");
            if (classP != null) {
                this.classP = classP;
                Log.w(TAG, "classP.getEndDate(): "+classP.getEndDate() );
            }
        }


        recyclerView = findViewById(R.id.rcv_student);
        tv_empty = findViewById(R.id.tv_empty);

        getStudentInClass();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new AttendanceStudentAdapter(AttendanceStudentActivity.this,arrayList,tv_empty);
        recyclerView.setAdapter(mainAdapter);

        //set title toolbar
        getSupportActionBar().setTitle("Attendance Student");

        //reload data in fragment
        swipeRefreshLayout =findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStudentInClass();
                mainAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private void getStudentInClass() {
        ApiClient.getStudentService().AttendanceStudent(classP.getId()).enqueue(new Callback<List<AttendanceStudent>>() {
            @Override
            public void onResponse(Call<List<AttendanceStudent>> call, Response<List<AttendanceStudent>> response) {
                if(response.isSuccessful()){
                    arrayList = response.body();
                    Log.w("AttendanceStudentActivi", "isSuccessful: "+arrayList.size() );

                    tv_empty =findViewById(R.id.tv_empty);
                    AttendanceStudentAdapter attendanceStudentAdapter =
                            new AttendanceStudentAdapter(AttendanceStudentActivity.this,arrayList,tv_empty);
                    recyclerView.setAdapter(attendanceStudentAdapter);


                }
            }

            @Override
            public void onFailure(Call<List<AttendanceStudent>> call, Throwable t) {
                Toast.makeText(AttendanceStudentActivity.this, "onFailure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AttendanceStudentActivi", "onFailure: "+t.getMessage());
            }
        });
    }
}