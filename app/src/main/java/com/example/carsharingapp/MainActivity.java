package com.example.carsharingapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //navigation
        configNavigation();
        navigationView = findViewById(R.id.nav_view);
        openDefaultFragment(savedInstanceState);
        navigationView.setNavigationItemSelectedListener(navItemSelectedListener());
    }

    private NavigationView.OnNavigationItemSelectedListener navItemSelectedListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        currentFragment = new HomeFragment();
                        //Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_profile:
                        currentFragment = new ProfileFragment();
                        //Toast.makeText(getApplicationContext(), "profile", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_rides:
                        currentFragment = new RidesFragment();
                        //Toast.makeText(getApplicationContext(), "rides", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_credit_cards:
                        currentFragment = new CreditCardsFragment();
                        //Toast.makeText(getApplicationContext(), "credit cards", Toast.LENGTH_SHORT).show();
                        break;
                }
                openfragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private void configNavigation(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
    //puts the current fragment on the screen
    public  void openfragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_container, currentFragment)
                .commit();
    }
    public void openDefaultFragment(Bundle savedInsanceState){
        if(savedInsanceState==null){
            currentFragment = new HomeFragment();
            openfragment();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }
}