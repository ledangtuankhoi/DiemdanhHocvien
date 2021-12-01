package com.example.diemdanhhocvienandroid2.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diemdanhhocvienandroid2.HomeActivity;
import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private HomeActivity mHomeActivity;
    public static  final String TAG = StudentAdapter.class.getName();

    public StudentAdapter(List<Student> studentList){
        this.studentList = studentList;
        Log.w(TAG, "StudentAdapter: studentlist"+studentList.size());
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student,parent,false);
        return new StudentAdapter.StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        Log.w(TAG, "onBindViewHolder: "+studentList.get(position).getId() );

        holder.tv_holyName.setText(student.getHolyName());
        String fullName = student.getFirstName()+" "+student.getLastName();
        int maxLengthTextViewFullName = 21;
        if(fullName.length()>maxLengthTextViewFullName){

        }
        holder.tv_fullname.setText(fullName);
        String info = student.getNumPhone();
        holder.tv_info.setText(info);
    }

    @Override
    public int getItemCount() {
        if(studentList != null){
            return studentList.size();
        }
        return 0;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fullname,tv_info,tv_order,tv_holyName;
        private ImageView img;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_fullname = itemView.findViewById(R.id.tv_fullname);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_order = itemView.findViewById(R.id.tv_order);
            tv_holyName =itemView.findViewById(R.id.tv_holyName);
            img = itemView.findViewById(R.id.img_student);
        }
    }

}