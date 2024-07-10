package com.example.taskdemo.ui.bottomNavigation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ActivityBottomNavigationBinding;
import com.example.taskdemo.databinding.ActivityMainBinding;

public class BottomNavigation extends AppCompatActivity {
    private ActivityBottomNavigationBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}