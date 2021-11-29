package com.example.diemdanhhocvienandroid2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diemdanhhocvienandroid2.fragment.ClassFragment;
import com.example.diemdanhhocvienandroid2.fragment.HomeFragment;
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

    private int currentFragment = FRAMGENT_CLASS;
    NavigationView navigationView;
    private RecyclerView rcvUser;

    //floating action button become a menu
    private MaterialSheetFab materialSheetFab;



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

        setTitleToolbar();
        replateFragment(new ClassFragment());
        navigationView.setCheckedItem(R.id.nav_class);


        //floating action button become a menu
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.fab_sheet_color);
        int fabColor = getResources().getColor(R.color.fab_color);

        // Initialize material sheet FAB
        materialSheetFab = new MaterialSheetFab(fab, sheetView, overlay,
                sheetColor, fabColor);
        TextView fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "add", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void replateFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
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
        }  else if (id == R.id.nav_class) {
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
            case FRAMGENT_GALLERY:
                title = getString(R.string.menu_gallery);

                break;
            case FRAMGENT_SLIDESSHOW:
                title = getString(R.string.menu_slideshow);

                break;
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}