package com.example.diemdanhhocvienandroid2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diemdanhhocvienandroid2.HomeActivity;
import com.example.diemdanhhocvienandroid2.R;
import com.example.diemdanhhocvienandroid2.adapter.ClassAdapter;
import com.example.diemdanhhocvienandroid2.api.ApiClient;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassFragment extends Fragment {

    public static final String TAG = ClassFragment.class.getName();
    private RecyclerView rcvClass;
    private View mView;
    private HomeActivity mHomeActivity;
    private List<ClassP> lstClass = new ArrayList<>();
    private User user = HomeActivity.user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_class, container, false);
        mHomeActivity = (HomeActivity) getActivity();

        rcvClass = mView.findViewById(R.id.rcv_class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mHomeActivity);
        rcvClass.setLayoutManager(linearLayoutManager);

        // authen get list class
        if (user.getRoleName().equals("teacher")) {
            getListClassOfTeacher(user.getUserId());
            Log.d(TAG, "getListClassOfTeacher--------- ");
         } else {
            getListClass();
            Log.d(TAG, "getListClass--------- ");
        }



        //floating action button become a menu
        View sheetView = mView.findViewById(R.id.fab_sheet);
        View overlay = mView.findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.fab_sheet_color);
        int fabColor = getResources().getColor(R.color.fab_color);
        FloatingActionButton fab = mView.findViewById(R.id.fab);
        // Initialize material sheet FAB
        MaterialSheetFab materialSheetFab = new MaterialSheetFab(fab, sheetView, overlay,
                sheetColor, fabColor);


        return mView;
    }


    private void getListClass() {
        ApiClient.getClassService().ClassIndex().enqueue(new Callback<List<ClassP>>() {
            @Override
            public void onResponse(Call<List<ClassP>> call, Response<List<ClassP>> response) {
                if(response.isSuccessful()){
                    lstClass = response.body();
                    Log.d("onResponse", "call api success" + lstClass.size() + " ");

                    ClassAdapter classAdapter = new ClassAdapter(lstClass, new ClassAdapter.IClickItemListener() {
                        @Override
                        public void onClickItemUser(ClassP classP) {
                            mHomeActivity.goToListOfClassFragment(classP);
                        }
                    });
//                 Log.d("onCreateView", "onCreateView"+lstClass.size() + " ");

                    rcvClass.setAdapter(classAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<ClassP>> call, Throwable t) {
                Toast.makeText(mHomeActivity.getApplicationContext(), "call api false \n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("onFailure", t.getMessage());

            }
        });
    }

    private void getListClassOfTeacher(int idTeacher) {
        ApiClient.getClassService().ClassIndexTeacher(idTeacher).enqueue(new Callback<List<ClassP>>() {
            @Override
            public void onResponse(Call<List<ClassP>> call, Response<List<ClassP>> response) {
                if(response.isSuccessful()){
                    Log.d("onResponse", "call api success" + lstClass.size() + " ");
                    lstClass = response.body();

                    //click item class tran to list student of class
                    ClassAdapter classAdapter = new ClassAdapter(lstClass, new ClassAdapter.IClickItemListener() {
                        @Override
                        public void onClickItemUser(ClassP classP) {
                            mHomeActivity.goToListOfClassFragment(classP);
                        }
                    });
//                 Log.d("onCreateView", "onCreateView"+lstClass.size() + " ");

                    rcvClass.setAdapter(classAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<ClassP>> call, Throwable t) {
                Toast.makeText(mHomeActivity.getApplicationContext(), "call api false \n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("onFailure", t.getMessage());

            }
        });
    }

}