package com.example.diemdanhhocvienandroid2.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.api.ApiClient;
import com.example.diemdanhhocvienandroid2.models.Student;
import com.example.diemdanhhocvienandroid2.viewmodel.StudentDelmultipleViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRemoveMultipleInClassAdapter extends RecyclerView.Adapter<StudentRemoveMultipleInClassAdapter.StudentViewHolder> {

    List<Student> studentList;
    public static final String TAG = StudentRemoveMultipleInClassAdapter.class.getName();


    //select multi
    StudentDelmultipleViewModel mainViewModel;
    boolean isEnable = false;
    boolean isSelectAll = false;
    Activity activity;
    TextView tv_empty;
    List<Student> selectList = new ArrayList<>();


    public StudentRemoveMultipleInClassAdapter(Activity activity, List<Student> ls ) {
        this.activity = activity;
        this.studentList = ls;


    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
//
        //init view model
        mainViewModel = ViewModelProviders.of((FragmentActivity) activity)
                .get(StudentDelmultipleViewModel.class);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);


        holder.tv_holyName.setText(student.getHolyName());
        String fullName = student.getFirstName() + " " + student.getLastName();
        int maxLengthTextViewFullName = 21;
        if (fullName.length() > maxLengthTextViewFullName) {

        }
        holder.tv_fullname.setText(fullName);
        String info = student.getNumPhone();
        holder.tv_info.setText("numberPhone: " + info);


        holder.relativeLayout.setBackgroundColor(Color.rgb(191, 174, 177));


        //set actin click shot
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEnable) {
                    ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                            //init menu inflater
                            MenuInflater menuInflater = mode.getMenuInflater();
                            //init menu
                            menuInflater.inflate(R.menu.menu_delete_multiple, menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            //when action prepare
                            isEnable = true;
                            //create
                            clickItem(holder);
//                            set observer on get text method
                            mainViewModel.getText().observe((LifecycleOwner) activity
                                    , new Observer<String>() {
                                        @Override
                                        public void onChanged(String s) {
                                            //when text change
                                            //set text on action model title

                                            mode.setTitle(String.format("%s/" + studentList.size() + " selected", s));


                                        }
                                    });
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            //when click on action mode item
                            //get item id
                            int id = item.getItemId();
                            //use swicth
                            switch (id) {
                                case R.id.menu_del_multiple:
//
                                    // when array list empty
                                    if (studentList.isEmpty()) {
                                        tv_empty.setVisibility(View.GONE);
                                    }

                                    RemoveStudent(selectList);

                                    mode.finish();
                                    break;

                                case R.id.menu_selete_all:
                                    if (selectList.size() == studentList.size()) {
                                        isEnable = false;
                                        selectList.clear();
                                        isSelectAll = false;

                                    } else {
                                        isEnable = true;
                                        selectList.clear();
                                        selectList.addAll(studentList);
                                        isSelectAll = true;
                                    }
                                    mainViewModel.setText(String.valueOf(selectList.size()));
                                    notifyDataSetChanged();
                                    break;
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            isEnable = false;
                            isSelectAll = false;
                            selectList.clear();
                            notifyDataSetChanged();
                        }
                    };
                    //start  action mode
                    ((AppCompatActivity) v.getContext()).startActionMode(callback);
//                    ((AppCompatActivity) v.getContext()).startActionMode(callback);

                } else {
                    //when action already enable
                    //call method
                    clickItem(holder);

                }
            }
        });


        //check condition
        if (isSelectAll) {
            //when item is select all value
            //visible all check box
            holder.iv_check_box.setVisibility(View.VISIBLE);
            //set backgound
            holder.relativeLayout.setBackgroundResource(R.drawable.bg1);


        } else {
            //when item is unselect
            //visible all un check box
            holder.iv_check_box.setVisibility(View.GONE);
            //set backgound
            holder.relativeLayout.setBackgroundColor(Color.rgb(191, 174, 177));
        }
    }


    private void RemoveStudent(List<Student> selectList) {
        for (Student a : selectList) {
            //set empty classid
            a.setClassId(0);
            //call api
            ApiClient.getStudentService().PutStudent(a.getId(), a).enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.isSuccessful()) {
//                        Log.w(TAG, "onResponse: "+response.body() );
                        studentList.remove(a);
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }
    private void clickItem(StudentViewHolder holder) {
        //get select item value
        Student s = studentList.get(holder.getAdapterPosition());
        //check condition
        if (holder.iv_check_box.getVisibility() == View.GONE) {
            //when item not select
            holder.iv_check_box.setVisibility(View.VISIBLE);
            //set backgound item
            holder.relativeLayout.setBackgroundResource(R.drawable.bg1);
            //add item selete list

            selectList.add(s);
        } else {
            //when item select
            holder.iv_check_box.setVisibility(View.GONE);
            //set backgound item
            holder.relativeLayout.setBackgroundColor(Color.rgb(191, 174, 177));
            //remove item list select

            selectList.remove(s);
        }
        //set text on view model
        mainViewModel.setText(String.valueOf(selectList.size()));

    }

    @Override
    public int getItemCount() {
        if (studentList != null) {
            return studentList.size();
        }
        return 0;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fullname, tv_info, tv_order, tv_holyName;
        private ImageView img_student, iv_check_box;
        private RelativeLayout relativeLayout;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_fullname = itemView.findViewById(R.id.tv_fullname);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_order = itemView.findViewById(R.id.tv_order);
            tv_holyName = itemView.findViewById(R.id.tv_holyName);
            img_student = itemView.findViewById(R.id.img_student);
            iv_check_box = itemView.findViewById(R.id.iv_check_box);
            relativeLayout = itemView.findViewById(R.id.item);
        }
    }
}
