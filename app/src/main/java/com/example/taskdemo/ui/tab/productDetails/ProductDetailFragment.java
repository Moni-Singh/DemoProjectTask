package com.example.taskdemo.ui.tab.productDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentProductDetailBinding;
import com.google.gson.Gson;

public class ProductDetailFragment extends Fragment {

    private ProductDetailViewModel mViewModel;
    private FragmentProductDetailBinding binding;
    private ProductDetailsAdapter productDetailsAdapter;

    public static ProductDetailFragment newInstance() {
        return new ProductDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Product");
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewModel.getProductsApi();

        observeViewModel();

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.productDetailsVp.setCurrentItem(getItem(+1), true);

            }
        });

        binding.btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.productDetailsVp.setCurrentItem(getItem(-1), true);
            }
        });

        return root;
    }

    private void observeViewModel() {
        mViewModel.getProductCategoryLiveData().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                Gson gson = new Gson();
                String response = gson.toJson(products);
                Log.d("responseDataProduct", response);
                productDetailsAdapter = new ProductDetailsAdapter(products);
                binding.productDetailsVp.setAdapter(productDetailsAdapter);
            }
        });
    }

    private int getItem(int offset) {
        int currentItem = binding.productDetailsVp.getCurrentItem();
        int totalItems = productDetailsAdapter != null ? productDetailsAdapter.getItemCount() : 0;
        int newItem = currentItem + offset;

        if (newItem < 0) {
            newItem = 0;
        } else if (newItem >= totalItems) {
            newItem = totalItems - 1;
        }

        return newItem;
    }
}
