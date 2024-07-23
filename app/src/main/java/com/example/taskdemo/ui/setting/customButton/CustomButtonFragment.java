package com.example.taskdemo.ui.setting.customButton;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentCustomButtonBinding;
import com.example.taskdemo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CustomButtonFragment extends Fragment {

    private FragmentCustomButtonBinding binding;
    private List<Integer> buttonNumbers;
    private ButtonAdapter buttonAdapter;
    private RecyclerView recyclerView;

    public static CustomButtonFragment newInstance() {
        return new CustomButtonFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCustomButtonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        buttonNumbers = new ArrayList<>();
        buttonAdapter = new ButtonAdapter(requireContext(), buttonNumbers);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(Constants.CUSTOM_BUTTON);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),3));
        recyclerView.setAdapter(buttonAdapter);

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = binding.numberInput.getText().toString();

                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(requireContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
                    return;
                }

                int numberOfButtons;
                try {
                    numberOfButtons = Integer.parseInt(input); // Parse the input
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Invalid number", Toast.LENGTH_SHORT).show();
                    return;
                }

                createButtons(numberOfButtons);
            }
        });

        // Handle back press
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.navigation_Setting);
            }
        });
        return root;
    }

    private void createButtons(int number) {
        buttonNumbers.clear();
        for (int i = 1; i <= number; i++) {
            buttonNumbers.add(i);
        }
        buttonAdapter.notifyDataSetChanged();
    }
}
