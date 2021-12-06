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
import com.example.diemdanhhocvienandroid2.api.ApiClient;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    public String classname;
    private HomeActivity mHomeActivity;
    public static  final String TAG = StudentAdapter.class.getName();
    public IClickListener mIClickListener;
    public interface IClickListener{
        public void onClickDetail(Student student);
    }

    public StudentAdapter(List<Student> studentList,IClickListener listener){
        this.studentList = studentList;
        this.mIClickListener = listener;
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

        holder.tv_holyName.setText(student.getHolyName());
        String fullName = student.getFirstName()+" "+student.getLastName();
        int maxLengthTextViewFullName = 21;
        if(fullName.length()>maxLengthTextViewFullName){

        }
        holder.tv_fullname.setText(fullName);
        String info = student.getNumPhone();
        holder.tv_info.setText("Numberphone: "+info);
        holder.tv_order.setText("email: "+student.getEmail());


        //set class name
        ApiClient.getClassService().GetClass(student.getClassId()).enqueue(new Callback<ClassP>() {
            @Override
            public void onResponse(Call<ClassP> call, Response<ClassP> response) {
                if(response.isSuccessful()){
                    ClassP s = new ClassP();
                    s = response.body();
                    if(s==null){
                        holder.tv_class.setText("null");
                    }else {
                        holder.tv_class.setText(response.body().getClassName());
                    }

                }
            }

            @Override
            public void onFailure(Call<ClassP> call, Throwable t) {
                Log.w(TAG, "onFailure: "+t.getMessage() );
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 //                this.registerForContextMenu();
                mIClickListener.onClickDetail(student);
                Log.w(TAG, "onClick: "+String.valueOf(student.getId( )));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(studentList != null){
            return studentList.size();
        }
        return 0;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fullname,tv_info,tv_order,tv_holyName, tv_class;
        private ImageView img;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_fullname = itemView.findViewById(R.id.tv_fullname);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_order = itemView.findViewById(R.id.tv_order);
            tv_holyName =itemView.findViewById(R.id.tv_holyName);
            img = itemView.findViewById(R.id.img_student);
            tv_class = itemView.findViewById(R.id.tv_class);
        }
    }

    public String getClassname(int idclass){
         String[] classname = new String[1];

        return classname[0];
    }

}
