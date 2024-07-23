package com.example.taskdemo.ui.setting.expandableList;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentExpandableListBinding;
import com.example.taskdemo.model.category.CategoryModel;
import com.example.taskdemo.model.category.Product;
import com.example.taskdemo.productinterface.OnClickCategoryProduct;
import com.example.taskdemo.utils.Constants;
import com.example.taskdemo.utils.HelperMethod;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListFragment extends Fragment implements OnClickCategoryProduct {

    private ExpandableListViewModel mViewModel;
    private FragmentExpandableListBinding binding;

    private RecyclerView recyclerView;
    private List<CategoryModel> categoryList;
    private CategoryAdapter adapter;
    private Context mContext;
    private String categories;
    private View progressLayout;
    private boolean isDataLoaded = false;

    public static ExpandableListFragment newInstance() {
        return new ExpandableListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentExpandableListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mContext = getContext();
        progressLayout = binding.progressLayout.getRoot();
        recyclerView = binding.categoryMainRv;
        recyclerView.setHasFixedSize(true);
        mViewModel = new ViewModelProvider(this).get(ExpandableListViewModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        categoryList = new ArrayList<>();
        SwipeRefreshLayout swipeRefreshLayout = binding.swipeRefreshlayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getProductCategoryApi();
            }
        });

        mViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), productCategory -> {
            if (productCategory != null) {
                swipeRefreshLayout.setRefreshing(false);
                progressLayout.setVisibility(View.GONE);
                categoryList.clear();
                productCategory.forEach(it -> {
                    categoryList.add(new CategoryModel(new ArrayList<Product>(), it.getName(), it.getId()));
                });
                adapter = new CategoryAdapter(categoryList, mViewModel, this, this, progressLayout, mContext);
                recyclerView.setAdapter(adapter);
                isDataLoaded = true;
            } else {
                swipeRefreshLayout.setRefreshing(false);
                progressLayout.setVisibility(View.GONE);
                HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
            }
        });

        if (!isDataLoaded) {
            progressLayout.setVisibility(View.VISIBLE);
            mViewModel.getProductCategoryApi();
        }else{
            progressLayout.setVisibility(View.GONE);
        }

        return root;
    }

    @Override
    public void onItemClick(int categoryId, Product product, int productId) {
        if (product != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.CATEGORY_PRODUCT_DETAILS, product);
            bundle.putInt(Constants.CATEGORY_PRODUCT_ID, productId);
            bundle.putInt(Constants.CATEGORY_ID, categoryId);
            Navigation.findNavController(requireView()).navigate(R.id.navigation_category_product_details, bundle);
        } else {
            HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
        }
    }
}
