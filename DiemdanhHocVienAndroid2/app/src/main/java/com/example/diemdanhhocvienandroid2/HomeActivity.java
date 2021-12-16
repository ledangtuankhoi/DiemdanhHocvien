package com.example.diemdanhhocvienandroid2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diemdanhhocvienandroid2.fragment.ClassFragment;
import com.example.diemdanhhocvienandroid2.fragment.HomeFragment;
import com.example.diemdanhhocvienandroid2.fragment.StudentAddMultipleInClassFragment;
import com.example.diemdanhhocvienandroid2.fragment.StudentDelMultipleFragment;
import com.example.diemdanhhocvienandroid2.fragment.StudentDetailFragment;
import com.example.diemdanhhocvienandroid2.fragment.StudentEditFragment;
import com.example.diemdanhhocvienandroid2.fragment.StudentFragment;
import com.example.diemdanhhocvienandroid2.fragment.StudentRemoveMultipleInClassFragment;
import com.example.diemdanhhocvienandroid2.fragment.StudentcreateFragment;
import com.example.diemdanhhocvienandroid2.fragment.StudentOfClassFragment;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.Student;
import com.example.diemdanhhocvienandroid2.models.User;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final int FRAMGENT_HOME = 1;
    private static final int FRAMGENT_GALLERY = 2;
    private static final int FRAMGENT_SLIDESSHOW = 3;
    private static final int FRAMGENT_USER = 4;
    private static final int FRAMGENT_CLASS = 5;
    private static final int FRAMGENT_STUDENT = 6;
    private static final String TAG = HomeActivity.class.getName();

    public int currentFragment = FRAMGENT_CLASS;
    NavigationView navigationView;
    private RecyclerView rcvUser;


    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // response bundle form login
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
//        Bundle bundle = getArguments();
        if (bundle != null) {
            User item = (User) bundle.getSerializable("object_user");
            if (item != null) {
                user = item;
                Toast.makeText(HomeActivity.this, "wellcom " + item.getLastName() + " " + item.getFirstName(), Toast.LENGTH_SHORT).show();
            } else Toast.makeText(HomeActivity.this, "user null", Toast.LENGTH_SHORT).show();

        } else Toast.makeText(HomeActivity.this, "bundel null", Toast.LENGTH_SHORT).show();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        //set menu home drawer for teacher
        if (user.getRoleName().equals("teacher")) {
            navigationView.inflateMenu(R.menu.activity_home_drawer_teacher);
        }else if(user.getRoleName().equals("leader")){
            navigationView.inflateMenu(R.menu.activity_home_drawer_leader);
        }else{
            navigationView.inflateMenu(R.menu.activity_home_drawer_admin);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        //set name header nav
        View header = navigationView.getHeaderView(0);
        TextView txEmail = header.findViewById(R.id.txEmail);
        TextView txFullName = header.findViewById(R.id.txFullName);
        txEmail.setText(user.getEmail());
        txFullName.setText(user.getLastName() + " " + user.getFirstName());


        //send data object user to classfragment
        ClassFragment classFragment = new ClassFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("object_user", user);
        classFragment.setArguments(bundle1);


        currentFragment = FRAMGENT_CLASS;
        setTitleToolbar();
        replateFragment(new ClassFragment());
        navigationView.setCheckedItem(R.id.nav_class);


    }


    public void goToStudentEditFragment(Student student) {

        StudentEditFragment studentEditFragment = new StudentEditFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_student", (Serializable) student);
        studentEditFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, studentEditFragment);
        fragmentTransaction.addToBackStack(StudentDetailFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToStudentDetailFragment(Student student) {

        StudentDetailFragment studentDetailFragment = new StudentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_student", (Serializable) student);
        studentDetailFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, studentDetailFragment);
        fragmentTransaction.addToBackStack(StudentDetailFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToStudentAddMultipleInClassFagment(ClassP classP) {

        StudentAddMultipleInClassFragment studentAddMultipleInClassFragment = new StudentAddMultipleInClassFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_class", (Serializable) classP);
        studentAddMultipleInClassFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, studentAddMultipleInClassFragment);
        fragmentTransaction.addToBackStack(StudentcreateFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToStudentRemoveMultipleInClassFagment(List<Student> studentList) {

        StudentRemoveMultipleInClassFragment studentRemoveMultipleInClassFragment = new StudentRemoveMultipleInClassFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("List_object_student", (Serializable) studentList);
        studentRemoveMultipleInClassFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, studentRemoveMultipleInClassFragment);
        fragmentTransaction.addToBackStack(StudentcreateFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToStudentDeleteMultipleFagment(List<Student> studentList) {

        StudentDelMultipleFragment studentDelMultipleFragment = new StudentDelMultipleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("List_object_student", (Serializable) studentList);
        studentDelMultipleFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, studentDelMultipleFragment);
        fragmentTransaction.addToBackStack(StudentcreateFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToStudentCreateFagment() {

        StudentcreateFragment studentFragment = new StudentcreateFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, studentFragment);
        fragmentTransaction.addToBackStack(StudentcreateFragment.TAG);
        fragmentTransaction.commit();
    }


    public void goToAttendanceStudent(ClassP classP) {
        Intent intent = new Intent(getApplicationContext(), AttendanceStudentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_class", classP);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void goToListOfClassFragment(ClassP classP) {
        StudentOfClassFragment studentOfClassFragment = new StudentOfClassFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_class", classP);
        studentOfClassFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, studentOfClassFragment);
        fragmentTransaction.addToBackStack(StudentOfClassFragment.TAG);
        fragmentTransaction.commit();
    }


    public void replateFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    public void goToLogin(){
        user = null;
        Intent intent = new Intent(getApplicationContext(), account_login.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        switch (id){
            case R.id.nav_home:
                if (FRAMGENT_HOME != currentFragment) {
                    replateFragment(new HomeFragment());
                    currentFragment = FRAMGENT_HOME;
                    navigationView.setCheckedItem(R.id.nav_home);
                }
                break;
            case R.id.nav_class:
                    replateFragment(new ClassFragment());
                    currentFragment = FRAMGENT_CLASS;
                    navigationView.setCheckedItem(R.id.nav_class);
                break;
            case R.id.nav_student:
                replateFragment(new StudentFragment());
                currentFragment = FRAMGENT_STUDENT;
                navigationView.setCheckedItem(R.id.nav_student);
                break;
            case R.id.nav_statistical:

                break;
            case R.id.nav_teacher:

                break;
            case R.id.nav_user:

                break;
            case R.id.nav_acount:

                break;
            case R.id.nav_logout:
                goToLogin();
                 break;
        }

        setTitleToolbar();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setTitleToolbar() {
        String title = "";
        switch (currentFragment) {
            case FRAMGENT_HOME:
                title = getString(R.string.menu_home);
                break;
            case FRAMGENT_CLASS:
                title = getString(R.string.menu_class);
                break;
            case FRAMGENT_STUDENT:
                title = getString(R.string.menu_student);
                break;

        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

}