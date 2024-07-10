package com.example.taskdemo.ui.userDetail;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentUserDetailBinding;
import com.example.taskdemo.model.userProfile.UserProfile;
import com.example.taskdemo.utils.ApplicationSharedPreferences;

public class UserDetailFragment extends Fragment {

    private FragmentUserDetailBinding binding;

    public static UserDetailFragment newInstance() {
        return new UserDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(R.string.user_detail);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        ApplicationSharedPreferences prefs = new ApplicationSharedPreferences(requireContext());
        UserProfile userProfile = prefs.getUserProfile();

        if (userProfile != null) {
            binding.progresslayoutFl.setVisibility(View.VISIBLE);

            // Load profile image
            Glide.with(this)
                    .load(userProfile.getProfileImage() != null && !userProfile.getProfileImage().isEmpty() ? Uri.parse(userProfile.getProfileImage()) : R.drawable.nature)
                    .placeholder(R.drawable.ic_person) // Placeholder image
                    .error(R.drawable.ic_person) // Error image
                    .into(binding.userProfileImage);

            // Load background image
            Glide.with(this)
                    .load(userProfile.getBackgroundImage() != null && !userProfile.getBackgroundImage().isEmpty() ? Uri.parse(userProfile.getBackgroundImage()) : R.drawable.background)
                    .placeholder(R.drawable.background) // Placeholder image
                    .error(R.drawable.background) // Error image
                    .into(binding.userBackgroundImage);

            binding.tvUserName.setText(userProfile.getName());
            binding.tvUserEmail.setText(userProfile.getEmail());
            binding.tvUserAddress.setText(userProfile.getAddress());
            binding.tvUserBio.setText(userProfile.getBio());
            binding.tvUserPhoneNo.setText(userProfile.getPhoneNo());

            // Hide progress layout
            binding.progresslayoutFl.setVisibility(View.GONE);

            binding.btnEditProfile.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable("userProfile", userProfile);
                bundle.putBoolean("isEditProfile", true);
                Navigation.findNavController(requireView()).navigate(R.id.navigation_user_profile, bundle);
            });
        } else {
            // Handle the case where userProfile is null
            binding.progresslayoutFl.setVisibility(View.GONE);
        }

        // Handle back press
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.navigation_Setting);
            }
        });

        return root;
    }
}
