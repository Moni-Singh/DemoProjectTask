package com.example.taskdemo.ui.userDetail;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentUserDetailBinding;
import com.example.taskdemo.model.userProfile.UserProfile;
import com.example.taskdemo.utils.ApplicationSharedPreferences;
import com.example.taskdemo.utils.Constants;

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

            Glide.with(this)
                    .load(userProfile.getProfileImage() != null && !userProfile.getProfileImage().isEmpty() ? Uri.parse(userProfile.getProfileImage()) : R.drawable.nature)
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(binding.userProfileImage);

            Glide.with(this)
                    .load(userProfile.getBackgroundImage() != null && !userProfile.getBackgroundImage().isEmpty() ? Uri.parse(userProfile.getBackgroundImage()) : R.drawable.background)
                    .placeholder(R.drawable.background)
                    .error(R.drawable.background)
                    .into(binding.userBackgroundImage);

            binding.tvUserName.setText(userProfile.getName());
            binding.tvUserEmail.setText(userProfile.getEmail());
            binding.tvUserAddress.setText(userProfile.getAddress());
            binding.tvUserBio.setText(userProfile.getBio());
            binding.tvUserPhoneNo.setText(userProfile.getPhoneNo());
            binding.progresslayoutFl.setVisibility(View.GONE);

            binding.btnEditProfile.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.USER_PROFILE, userProfile);
                bundle.putBoolean(Constants.IS_EDIT_PROFILE, true);
                Navigation.findNavController(requireView()).navigate(R.id.navigation_user_profile, bundle);
            });
        } else {
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
