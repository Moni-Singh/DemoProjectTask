package com.example.taskdemo.ui.tab.productDetails;

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
import com.example.taskdemo.databinding.FragmentProductDetailBinding;
import com.example.taskdemo.model.response.Product;
import com.example.taskdemo.utils.Constants;
import com.example.taskdemo.utils.HelperMethod;

public class ProductDetailFragment extends Fragment {

    private ProductDetailViewModel mViewModel;
    private FragmentProductDetailBinding binding;
    private ProductDetailsAdapter productDetailsAdapter;
    private Product product;
    private Context mContext;
    private int productId;
    private View progressLayout;


    public static ProductDetailFragment newInstance() {
        return new ProductDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mContext = getContext();
        progressLayout = binding.progressLayout.getRoot();
        mViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        progressLayout.setVisibility(View.VISIBLE);

        // Set up the toolbar
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(R.string.product_details);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        binding.toolbar.setTitleTextColor(Color.WHITE);

        // Fetch product data from the API
        mViewModel.getProductsApi();

        // Observe the ViewModel for data changes
        observeViewModel();

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.productDetailsVp.setCurrentItem(getItem(-1), true);  // Move to the previous item
            }
        });

        binding.btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.productDetailsVp.setCurrentItem(getItem(1), true);  // Move to the next item
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

    // Observe the ViewModel for product data
    private void observeViewModel() {
        mViewModel.getProductCategoryLiveData().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                // Retrieve the product and position from the arguments
                Bundle bundle = getArguments();
                if (bundle != null) {
                    product = bundle.getParcelable(Constants.PRODUCT_DETAILS);
                    productId = bundle.getInt(Constants.PRODUCT_ID, 0);
                } else {
                    HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
                    return;
                }

                productDetailsAdapter = new ProductDetailsAdapter(products);
                binding.productDetailsVp.setAdapter(productDetailsAdapter);

                // Set offscreen page limit to default
                binding.productDetailsVp.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);

                // Find the position of the product with the given id
                int positionToDisplay = -1;
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getId() == productId) {
                        positionToDisplay = i;
                        break;
                    }
                }

                if (positionToDisplay != -1) {
                    binding.productDetailsVp.setCurrentItem(positionToDisplay, false);
                    binding.productDetailsVp.setUserInputEnabled(true); // Allow user input for swiping
                    progressLayout.setVisibility(View.GONE);
                } else {
                    progressLayout.setVisibility(View.GONE);
                    HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
                }
            }
        });
    }

    // Get the item position with the given offset
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
