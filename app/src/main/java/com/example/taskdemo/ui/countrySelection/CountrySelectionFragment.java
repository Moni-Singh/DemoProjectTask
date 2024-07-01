package com.example.taskdemo.ui.countrySelection;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentCountrySelectionBinding;
import com.example.taskdemo.model.country.response.Country;
import com.example.taskdemo.model.country.response.State;
import com.example.taskdemo.utils.HelperMethod;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CountrySelectionFragment extends Fragment {

    private CountrySelectionViewModel mViewModel;
    private FragmentCountrySelectionBinding binding;

    private String selectedCountry;
    private String selectedState;
    private String selectedCities;
    private Context mContext;

    public static CountrySelectionFragment newInstance() {
        return new CountrySelectionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCountrySelectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(CountrySelectionViewModel.class);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(R.string.country_selection);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        mViewModel.getCountryList();
        mContext = getContext();
        observeViewModel();

        // Handle back press
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.navigation_Setting);
            }
        });

        return root;
    }

    @SuppressLint("SetTextI18n")
    private void observeViewModel() {
        mViewModel.getContryListRespsneObserver().observe(getViewLifecycleOwner(), countryDataResponse -> {
            if (countryDataResponse != null) {
                List<String> countryNames = new ArrayList<>();
                for (Country country : countryDataResponse.getData()) {
                    countryNames.add(country.getCountry());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, countryNames);
                AutoCompleteTextView autocompleteTV = binding.selectCountryTextView;
                autocompleteTV.setAdapter(arrayAdapter);

                autocompleteTV.setOnItemClickListener((parent, view, position, id) -> {
                    selectedCountry = (String) parent.getItemAtPosition(position);
                    if (selectedCountry != null) {
                        selectedState = null;
                        selectedCities = null;
                        binding.selectStateTextView.setText(R.string.select_State);
                        binding.selectCitiesTextView.setText(R.string.select_city);
                        binding.displayCountySection.setVisibility(View.GONE);
                        mViewModel.getStateList(selectedCountry);
                    }
                });
            }
        });

        mViewModel.getStateListObserver().observe(getViewLifecycleOwner(), stateResponse -> {
            if (stateResponse != null && !stateResponse.error) {
                ArrayList<String> stateNames = new ArrayList<>();
                if (stateResponse.getData() != null && stateResponse.getData().getStates() != null) {
                    for (State state : stateResponse.getData().getStates()) {
                        stateNames.add(state.getName());
                    }
                }

                if (stateNames.isEmpty()) {
                    binding.selectStateTextView.setText(R.string.state_not_found);
                    binding.selectCitiesTextView.setText(R.string.city_not_found);
                    binding.selectStateTextView.setAdapter(null);
                    HelperMethod.showToast(getString(R.string.state_city_not_found), mContext);
                } else {
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, stateNames);
                    binding.selectStateTextView.setAdapter(arrayAdapter);
                    binding.selectStateTextView.setOnItemClickListener((parent, view, position, id) -> {
                        selectedState = (String) parent.getItemAtPosition(position);
                        if (selectedState != null && selectedCountry != null) {
                            selectedCities = null;
                            binding.selectCitiesTextView.setText(R.string.select_city);
                            binding.displayCountySection.setVisibility(View.GONE);
                            mViewModel.getCitiesList(selectedCountry, selectedState);
                        }
                    });
                }
            }
        });

        mViewModel.getCityListObserver().observe(getViewLifecycleOwner(), cityResponse -> {
            if (cityResponse != null) {
                if (!cityResponse.getData().isEmpty()) {
                    ArrayList<String> citiesName = cityResponse.getData();
                    String[] cityArray = citiesName.toArray(new String[0]);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, cityArray);
                    AutoCompleteTextView autocompleteTV = binding.selectCitiesTextView;
                    autocompleteTV.setAdapter(arrayAdapter);

                    autocompleteTV.setOnItemClickListener((parent, view, position, id) -> {
                        selectedCities = (String) parent.getItemAtPosition(position);

                        if (selectedState != null && selectedCountry != null && selectedCities != null) {
                            binding.displayCountySection.setVisibility(View.VISIBLE);
                            binding.tvState.setText("Selected State :" + selectedState);
                            binding.tvCity.setText("Selected City :" + selectedCities);
                            binding.tvCountry.setText("Selected Country : " + selectedCountry);
                        }
                    });
                } else {
                    binding.selectCitiesTextView.setText(R.string.city_not_found);
                    HelperMethod.showToast(getString(R.string.city_not_found), mContext);
                }
            } else {
                HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
            }
        });

    }
}
