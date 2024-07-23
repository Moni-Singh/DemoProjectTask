package com.example.taskdemo.ui.tab.expandable.productDetails;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentProductCategoryDetailsBinding;
import com.example.taskdemo.model.expandable.CategoryProductData;
import com.example.taskdemo.utils.Constants;
import com.example.taskdemo.utils.HelperMethod;

public class ProductCategoryDetails extends Fragment {

    private ProductCategoryDetailsViewModel mViewModel;
    private FragmentProductCategoryDetailsBinding binding;
    private Context mContext;
    private CategoryProductData product;
    private int productId;
    private String categoryName;
    private View progressLayout;
    private CategoryProductDetailsAdapter categoryProductDetailsAdapter;

    public static ProductCategoryDetails newInstance() {
        return new ProductCategoryDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductCategoryDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mContext = getContext();
        progressLayout = binding.progressLayout.getRoot();
        progressLayout.setVisibility(View.VISIBLE);

        mViewModel = new ViewModelProvider(this).get(ProductCategoryDetailsViewModel.class);

        // Set up the toolbar
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(Constants.PRODUCT_DETAILS);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        // Retrieve arguments passed to the Fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            product = (CategoryProductData) bundle.getParcelable(Constants.CATEGORY_PRODUCT_DETAILS);
            productId = bundle.getInt(Constants.CATEGORY_PRODUCT_ID, 0);
            categoryName = bundle.getString(Constants.CATEGORY_NAME, "Default Category");
            mViewModel.getProductByCategory(categoryName);
            observeViewModel();
        }

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.categoryProductDetailsVp.setCurrentItem(getItem(-1), true);  // Move to the previous item
            }
        });

        binding.btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.categoryProductDetailsVp.setCurrentItem(getItem(1), true);  // Move to the next item
            }
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

    private void observeViewModel() {
        mViewModel.getCategoryProductLiveData().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                progressLayout.setVisibility(View.GONE);
                // Retrieve the product and position from the arguments
                Bundle bundle = getArguments();
                if (bundle != null) {
                    product = bundle.getParcelable(Constants.CATEGORY_PRODUCT_DETAILS);
                    productId = bundle.getInt(Constants.CATEGORY_PRODUCT_ID, 0);
                } else {
                    HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
                }

                categoryProductDetailsAdapter = new CategoryProductDetailsAdapter(products);
                binding.categoryProductDetailsVp.setAdapter(categoryProductDetailsAdapter);

                // Set offscreen page limit to 0
                binding.categoryProductDetailsVp.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);

                // Finding the position of the product with the given id
                int positionToDisplay = -1;
                for (int i = 0; i < products.products.size(); i++) {
                    if (products.products.get(i).getId() == productId) {
                        positionToDisplay = i;
                        break;
                    }
                }

                if (positionToDisplay != -1) {
                    binding.categoryProductDetailsVp.setCurrentItem(positionToDisplay, false);
                    binding.categoryProductDetailsVp.setUserInputEnabled(false);
                    progressLayout.setVisibility(View.GONE);
                } else {
                    progressLayout.setVisibility(View.GONE);
                    HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
                }
            } else {
                progressLayout.setVisibility(View.GONE);
            }
        });
    }

    // Get the new item index by adding an offset to the current item
    private int getItem(int offset) {
        int currentItem = binding.categoryProductDetailsVp.getCurrentItem();
        int totalItems = categoryProductDetailsAdapter != null ? categoryProductDetailsAdapter.getItemCount() : 0;
        int newItem = currentItem + offset;

        if (newItem < 0) {
            newItem = 0;
        } else if (newItem >= totalItems) {
            newItem = totalItems - 1;
        }
        return newItem;
    }
}
