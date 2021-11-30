package com.example.diemdanhhocvienandroid2.adapter;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diemdanhhocvienandroid2.HomeActivity;
import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.models.ClassP;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHollder> {
    private List<ClassP> lstClass;
    private IClickItemListener mIClickItemListener;
    private HomeActivity main2Activity;
    public static  final String TAG = ClassAdapter.class.getName();

    public interface IClickItemListener{
        void onClickItemUser(ClassP classP);
    }


    public ClassAdapter(List<ClassP> lstClass, IClickItemListener listener){
        this.lstClass = lstClass;
        this.mIClickItemListener = listener;
    }

    @NonNull
    @Override
    public ClassViewHollder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class,parent,false);
        return new ClassViewHollder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ClassViewHollder holder, int position) {
        ClassP classP = lstClass.get(position);
        if(classP == null){
            Toast.makeText(main2Activity.getApplicationContext(), "list null", Toast.LENGTH_SHORT).show();
            return;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemListener.onClickItemUser(classP);
            }
        });

        holder.className.setText(classP.getClassName());

        //conver string to date
        Date date1= null;

        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(classP.getEndDate());


        } catch (ParseException e) {
            e.printStackTrace();
        }


        //substring "yyyy-MM-dd'T'HH:mm:ss" to "yyyy-MM-dd"
        holder.startDate.setText(classP.getStartDate().substring(0,classP.getStartDate().indexOf("T")));
        holder.endDate.setText(classP.getEndDate().substring(0,classP.getEndDate().indexOf("T")));

        //day of week classs
        List<String> dayOfWeek = Arrays.asList(classP.getDayOfWeek().split(","));
        holder.dayOfWeek.setText("dayOfWeek:");
        for (String i :dayOfWeek){
            switch (i){
                case "0":
                    holder.dayOfWeek.append(" | CN");
                    break;

                case "1":
                    holder.dayOfWeek.append(" | 2");
                    break;

                case "2":
                    holder.dayOfWeek.append(" | 3");
                    break;

                case "3":
                    holder.dayOfWeek.append(" | 4");
                    break;

                case "4":
                    holder.dayOfWeek.append(" | 5");
                    break;

                case "5":
                    holder.dayOfWeek.append(" | 6");
                    break;

                case "6":
                    holder.dayOfWeek.append(" | 7");
                    break;

            }
        }
    }

    @Override
    public int getItemCount() {
        if(lstClass != null){
            return lstClass.size();
        }
        return 0;
    }

    public class ClassViewHollder extends RecyclerView.ViewHolder{


        private TextView className,codeName, startDate , endDate , dayOfWeek , userId ;


        public ClassViewHollder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.tv_class_name);
            startDate = itemView.findViewById(R.id.tv_class_startdate);
            endDate = itemView.findViewById(R.id.tv_class_enddate);
            dayOfWeek = itemView.findViewById(R.id.tv_class_dayofday);
          }
    }

}
