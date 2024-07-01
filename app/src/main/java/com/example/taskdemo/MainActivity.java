package com.example.taskdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Check if token exists and navigate accordingly
        ApplicationSharedPreferences sharedPreferences = new ApplicationSharedPreferences(this);
        String token = sharedPreferences.getToken();

        if (token != null) {
            // User is logged in, navigate to main tab
            navController.navigate(R.id.navigation_mainTab);
        } else {
            // User is not logged in, navigate to login screen
            navController.navigate(R.id.navigation_login);
        }

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.navigation_login) {
                    // Handle login destination
                } else if (navDestination.getId() == R.id.navigation_mainTab) {
                    // Handle main tab destination
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (navController != null) {
            return navController.navigateUp();
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (navController != null && !navController.popBackStack()) {
            super.onBackPressed();
        }
    }
}
