package com.example.carsharingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carsharingapp.asyncTask.AsyncTaskRunner;
import com.example.carsharingapp.asyncTask.Callback;
import com.example.carsharingapp.database.models.User;
import com.example.carsharingapp.database.service.UserService;
import com.example.carsharingapp.util.Car;
import com.example.carsharingapp.util.CarJSONParser;
import com.example.carsharingapp.util.City;
import com.example.carsharingapp.util.CityJSONParser;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment currentFragment;

    private User user;
    private  UserService userService;

   private InputStream inputStream;
   private InputStreamReader inputStreamReader;
   private BufferedReader bufferedReader;
   private HttpURLConnection connection;

   private ArrayList<Car> cars;
   private ArrayList<City> cities;
   Bundle savedInstance;

   private String json;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstance = savedInstanceState;
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(LoginActivity.LOGIN_SHARED_PREF, MODE_PRIVATE);
        //navigation
        configNavigation();
        navigationView = findViewById(R.id.nav_view);
        openDefaultFragment(savedInstanceState);
        navigationView.setNavigationItemSelectedListener(navItemSelectedListener());

        userService = new UserService(getApplicationContext());
        getUserFromDB();
        getJsonFromWeb("https://jsonkeeper.com/b/R66C");




    }

    private NavigationView.OnNavigationItemSelectedListener navItemSelectedListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:

                      if(cars != null && cities !=null) {
                          currentFragment = HomeFragment.newInstance(cars, cities);
                      }
                        //Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_profile:
                        if(user!= null){
                            currentFragment = ProfileFragment.newInstance(user);
                        }
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
                    case R.id.nav_logout:
                        logout();
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

        NavigationView navigationView =  findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView tvUsername =headerView.findViewById(R.id.tv_nav_username);
        String username = sharedPreferences.getString(LoginActivity.USER_NAME, "User");
        tvUsername.setText(getString(R.string.nav_header_user_name, username));


    }
    //puts the current fragment on the screen
    private  void openfragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_container, currentFragment)
                .commit();
    }

    private void openDefaultFragment(Bundle savedInsanceState){
        if(savedInsanceState==null){
            if(cars !=null && cities !=null) {
                currentFragment = HomeFragment.newInstance(cars, cities);
                openfragment();
                navigationView.setCheckedItem(R.id.nav_home);
            }

        }
    }

    private void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();

    }

    private void getUserFromDB(){
        long id = sharedPreferences.getLong(LoginActivity.USER_ID, -1);
        if(id != -1){
        userService.findUserById(id, new Callback<User>() {
            @Override
            public void runResultOnUiThread(User result) {
                if(result != null){
                    user = result;
                }
            }
        });

        }
    }

    private void getJsonFromWeb(String JsonAddress){
        AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {

                StringBuilder result = new StringBuilder();

                try {
                    URL url = new URL(JsonAddress);
                    connection = (HttpURLConnection) url.openConnection();
                    inputStream = connection.getInputStream();
                    inputStreamReader = new InputStreamReader(inputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connection.disconnect();

                }
                return result.toString();
            }

        };

        Callback<String> callback = new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {

                if(result != null) {
                    json = result;

                    cars = CarJSONParser.fromJson(json);
                    cities = CityJSONParser.fromJson(json);

                    Log.d("Main Activity JSON", result);

                    Log.d("LISTS", cars.toString());
                    Log.d("LISTSC", cities.toString());

                    openDefaultFragment(savedInstance);
                }




            }
        };

        asyncTaskRunner.executeAsync(callable, callback);
    }


}