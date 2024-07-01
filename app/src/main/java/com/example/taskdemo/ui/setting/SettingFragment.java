package com.example.taskdemo.ui.setting;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentSettingBinding;
import com.example.taskdemo.utils.ApplicationSharedPreferences;
import com.example.taskdemo.utils.Constants;
import com.example.taskdemo.utils.HelperMethod;

import java.util.List;

public class SettingFragment extends Fragment {

    private SettingViewModel mViewModel;
    private FragmentSettingBinding binding;
    private Context mContext;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        mContext = getContext();
        View root = binding.getRoot();
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(Constants.SETTING);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        binding.btnLogout.setOnClickListener(v -> {
            HelperMethod.showLogoutDialog(mContext, () -> {
                ApplicationSharedPreferences sharedPreferences = new ApplicationSharedPreferences(requireContext());
                sharedPreferences.clearToken();
                Navigation.findNavController(requireView())
                        .navigate(R.id.navigation_login);
                HelperMethod.showToast(getString(R.string.successfull_logout), mContext);
            });
        });

        // Handle back press
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.navigation_mainTab);
            }
        });
        return root;
    }

}