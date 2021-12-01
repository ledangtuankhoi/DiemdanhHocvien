package com.example.diemdanhhocvienandroid2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diemdanhhocvienandroid2.fragment.AttendanceStudentFragment;
import com.example.diemdanhhocvienandroid2.fragment.ClassFragment;
import com.example.diemdanhhocvienandroid2.fragment.HomeFragment;
import com.example.diemdanhhocvienandroid2.fragment.StudentOfClassFragment;
import com.example.diemdanhhocvienandroid2.models.ClassP;
import com.example.diemdanhhocvienandroid2.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import com.gordonwong.materialsheetfab.MaterialSheetFab;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final int FRAMGENT_HOME = 1;
    private static final int FRAMGENT_GALLERY = 2;
    private static final int FRAMGENT_SLIDESSHOW = 3;
    private static final int FRAMGENT_USER = 4;
    private static final int FRAMGENT_CLASS = 5;
    private static final String TAG = HomeActivity.class.getName();

    private int currentFragment = FRAMGENT_CLASS;
    NavigationView navigationView;
    private RecyclerView rcvUser;

    //floating action button become a menu
    public MaterialSheetFab materialSheetFab;

    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton fab = findViewById(R.id.fab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

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


        //set name header nav
        View header = navigationView.getHeaderView(0);
        TextView txEmail = header.findViewById(R.id.txEmail);
        TextView txFullName = header.findViewById(R.id.txFullName);
        txEmail.setText(user.getEmail());
        txFullName.setText(user.getLastName() + " " + user.getFirstName());


        //floating action button become a menu
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.fab_sheet_color);
        int fabColor = getResources().getColor(R.color.fab_color);

        // Initialize material sheet FAB
        materialSheetFab = new MaterialSheetFab(fab, sheetView, overlay,
                sheetColor, fabColor);


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

    @Override
    protected void onStop() {
        super.onStop();
        materialSheetFab.hideSheet();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (FRAMGENT_HOME != currentFragment) {
                replateFragment(new HomeFragment());
                currentFragment = FRAMGENT_HOME;
                navigationView.setCheckedItem(R.id.nav_home);

            }
        } else if (id == R.id.nav_class) {
            replateFragment(new ClassFragment());
            currentFragment = FRAMGENT_CLASS;
            navigationView.setCheckedItem(R.id.nav_class);
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

        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}