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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diemdanhhocvienandroid2.viewmodel.AttendanceStudentViewModel;
import com.example.diemdanhhocvienandroid2.HomeActivity;
import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.models.AttendanceStudent;

import java.util.ArrayList;
import java.util.List;

public class AttendanceStudentAdapter extends RecyclerView.Adapter<AttendanceStudentAdapter.StudentViewHolder> {

    List<AttendanceStudent> studentList;
    private HomeActivity mHomeActivity;
    public static  final String TAG = AttendanceStudentAdapter.class.getName();

    //select multi
    AttendanceStudentViewModel mainViewModel;
    boolean isEnable = false;
    boolean isSelectAll = false;
    Activity activity;
//    ArrayList<String> arrayList = new ArrayList<>();
    TextView tv_empty;
//    ArrayList<String> selectList = new ArrayList<>();
    List<AttendanceStudent> selectList = new ArrayList<>();


    public AttendanceStudentAdapter(Activity activity, List<AttendanceStudent> attendanceStudentList, TextView tv_empty) {
        this.activity = activity;
        this.studentList = attendanceStudentList;
        this.tv_empty = tv_empty;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student,parent,false);
//
        //init view model
        mainViewModel = ViewModelProviders.of((FragmentActivity) activity)
                .get(AttendanceStudentViewModel.class);
        return new  StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        AttendanceStudent student = studentList.get(position);


        holder.tv_holyName.setText(student.getHolyName());
        String fullName = student.getFirstName()+" "+student.getLastName();
        int maxLengthTextViewFullName = 21;
        if(fullName.length()>maxLengthTextViewFullName){

        }
        holder.tv_fullname.setText(fullName);
        String info = student.getNumPhone();
        holder.tv_info.setText("numberPhone: "+info);
        holder.tv_order.setText("order: "+String.valueOf(student.getOrder()));

        //set actin click shot
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEnable){
                    ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            //init menu inflater
                            MenuInflater menuInflater = mode.getMenuInflater();
                            //init menu
                            Log.w(TAG, "onCreateActionMode: menuInflater 1");
                            menuInflater.inflate(R.menu.menu_attendance,menu);
                            Log.w(TAG, "onCreateActionMode: menuInflater 2");
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
                                            mode.setTitle(String.format("%s/"+studentList.size()+" selected",s));
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
                            switch (id){
                                case R.id.menu_delete:
                                    //when click delete
                                    //use for loop
                                    Log.e("menu_delete", "onActionItemClicked: "+selectList.size() );
                                    for (AttendanceStudent s: selectList){
                                        //remove  selete item on select list
                                        studentList.remove(s);
                                    }
                                    // when array list empty
                                    if(studentList.isEmpty()){
                                        tv_empty.setVisibility(View.GONE);
                                    }
                                    mode.finish();
                                    break;
                                case R.id.menu_selete_all:
                                    if(selectList.size() == studentList.size()){
                                        isEnable = false;
                                        selectList.clear();
                                        isSelectAll = false;

                                    }else {
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
                            isEnable =false;
                            isSelectAll = false;
                            selectList.clear();
                            notifyDataSetChanged();
                        }
                    };
                    //start  action mode
                    ((AppCompatActivity) v.getContext()).startActionMode(callback);
//                    ((AppCompatActivity) v.getContext()).startActionMode(callback);

                }else  {
                    //when action already enable
                    //call method
                    clickItem(holder);

                }
            }
        });


        //check condition
        if(isSelectAll){
            //when item is select all value
            //visible all check box
            holder.iv_check_box.setVisibility(View.VISIBLE);
            //set backgound
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }else {
            //when item is unselect
            //visible all un check box
            holder.iv_check_box.setVisibility(View.GONE);
            //set backgound
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }



    private void clickItem(StudentViewHolder holder) {
        //get select item value
        AttendanceStudent s = studentList.get(holder.getAdapterPosition());
        //check condition
        if(holder.iv_check_box.getVisibility() == View.GONE){
            //when item not select
            holder.iv_check_box.setVisibility(View.VISIBLE);
            //set backgound item
            holder.itemView.setBackgroundColor(Color.LTGRAY);
            //add item selete list

            selectList.add(s);
        }else{
            //when item select
            holder.iv_check_box.setVisibility(View.GONE);
            //set backgound item
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            //remove item list select

            selectList.remove(s);
        }
        //set text on view model
        mainViewModel.setText(String.valueOf(selectList.size()));
        Log.e("clickItem", "clickItem: "+selectList.size());

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
        private ImageView img_student,iv_check_box;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_fullname = itemView.findViewById(R.id.tv_fullname);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_order = itemView.findViewById(R.id.tv_order);
            tv_holyName =itemView.findViewById(R.id.tv_holyName);
            img_student = itemView.findViewById(R.id.img_student);
            iv_check_box = itemView.findViewById(R.id.iv_check_box);
        }
    }

}
