package com.example.taskdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.taskdemo.databinding.ActivityMainBinding;
import com.example.taskdemo.utils.ApplicationSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    public static List<Integer> likeProducts = new ArrayList<>();
    public static List<Integer> bookmarked = new ArrayList<>();
    public  static int expandablePosition = -1;
    public static List<Integer> eventLocation = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri uri = getIntent().getData();

        if (uri != null) {
            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getPath();

            // Handle the URI here
            if ("myapp".equals(scheme) && "myhost".equals(host) && "/login".equals(path)) {
                Toast.makeText(this, R.string.welcome_to_task_demo, Toast.LENGTH_SHORT).show();
            }
        }


        // Check if token exists and navigate accordingly
        ApplicationSharedPreferences sharedPreferences = new ApplicationSharedPreferences(this);
        String token = sharedPreferences.getToken();

        if (token != null) {
            navController.navigate(R.id.navigation_mainTab);
        } else {
            navController.navigate(R.id.navigation_login);
        }
    }

    public static void setExpandedPosition(int position) {
        expandablePosition = position;
    }

    public static int getExpandedPosition() {
        return expandablePosition;
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (navController != null) {
            return navController.navigateUp();
        }
        return super.onSupportNavigateUp();
    }
}
