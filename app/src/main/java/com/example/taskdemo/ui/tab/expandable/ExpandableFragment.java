package com.example.taskdemo.ui.tab.expandable;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentExpandableBinding;
import com.example.taskdemo.model.expandable.CategoryExpandableModel;
import com.example.taskdemo.model.expandable.CategoryProductData;
import com.example.taskdemo.productinterface.OnClickCategoryItem;
import com.example.taskdemo.utils.Constants;
import com.example.taskdemo.utils.HelperMethod;

import java.util.ArrayList;
import java.util.List;

public class ExpandableFragment extends Fragment implements OnClickCategoryItem {

    private ExpandableViewModel mViewModel;
    private FragmentExpandableBinding binding;

    private RecyclerView recyclerView;
    private List<CategoryExpandableModel> categoryList;
    private ProductCategoryAdapter adapter;
    private Context mContext;
    private String categories;
    private View progressLayout;
    private boolean isDataLoaded = false;

    public static ExpandableFragment newInstance() {
        return new ExpandableFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentExpandableBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(ExpandableViewModel.class);
        View root = binding.getRoot();
        recyclerView = binding.productCategoryRv;
        progressLayout = binding.progressLayout.getRoot();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        categoryList = new ArrayList<>();

        // Set up swipe-to-refresh functionality
        SwipeRefreshLayout swipeRefreshLayout = binding.swipeRefreshlayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getProductsApi();
            }
        });

        // Observe changes in the ViewModel's live data
        mViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), productCategory -> {
            if (productCategory != null) {
                swipeRefreshLayout.setRefreshing(false);
                progressLayout.setVisibility(View.GONE);

                // Add categories to the list
                productCategory.forEach(it -> {
                    categoryList.add(new CategoryExpandableModel(new CategoryProductData(), it.getName(), it.getSlug()));
                });
                adapter = new ProductCategoryAdapter(categoryList, mViewModel, this, this, mContext);
                recyclerView.setAdapter(adapter);
                isDataLoaded = true;
            } else {
                swipeRefreshLayout.setRefreshing(false);
                progressLayout.setVisibility(View.GONE);
                HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
            }
        });

        // Check if data is already loaded; if not, request data
        if (!isDataLoaded) {
            progressLayout.setVisibility(View.VISIBLE);
            mViewModel.getProductsApi();
        } else {
            progressLayout.setVisibility(View.GONE);
        }

        return root;
    }

    // Handle item click events
    @Override
    public void onItemClick(String categoryName, CategoryProductData product, int productId) {
        if (product != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.CATEGORY_PRODUCT_DETAILS, product);
            bundle.putInt(Constants.CATEGORY_PRODUCT_ID, productId);
            bundle.putString(Constants.CATEGORY_NAME, categoryName);
            Navigation.findNavController(requireView()).navigate(R.id.navigation_category_product_details, bundle);
        } else {
            HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
        }
    }
}