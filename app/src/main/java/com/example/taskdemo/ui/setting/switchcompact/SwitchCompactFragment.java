package com.example.taskdemo.ui.setting.switchcompact;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentSwitchCompactBinding;
import com.example.taskdemo.utils.Constants;
import com.facebook.drawee.view.SimpleDraweeView;

public class SwitchCompactFragment extends Fragment {

    private SwitchCompactViewModel mViewModel;
    private FragmentSwitchCompactBinding binding;

    public static SwitchCompactFragment newInstance() {
        return new SwitchCompactFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSwitchCompactBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(SwitchCompactViewModel.class);
        View root = binding.getRoot();
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(Constants.SWITCH_COMPACT);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        // Handle back press
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.navigation_Setting);
            }
        });

        // Set switch to on by default
        binding.roundUpSwitch.setChecked(true);

        // Display the first image by default
        Uri defaultImageUri = Uri.parse("https://fastly.picsum.photos/id/13/2500/1667.jpg?hmac=SoX9UoHhN8HyklRA4A3vcCWJMVtiBXUg0W4ljWTor7s");
        SimpleDraweeView draweeView = binding.idSDimage;
        draweeView.setImageURI(defaultImageUri);

        // Handle switch change
        binding.roundUpSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Uri imageUri = Uri.parse("https://fastly.picsum.photos/id/13/2500/1667.jpg?hmac=SoX9UoHhN8HyklRA4A3vcCWJMVtiBXUg0W4ljWTor7s");
                draweeView.setImageURI(imageUri);
            } else {
                Uri imageUri = Uri.parse("https://fastly.picsum.photos/id/29/4000/2670.jpg?hmac=rCbRAl24FzrSzwlR5tL-Aqzyu5tX_PA95VJtnUXegGU");
                draweeView.setImageURI(imageUri);
            }
        });

        return root;
    }
}
