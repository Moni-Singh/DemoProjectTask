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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentExpandableListBinding;
import com.example.taskdemo.model.category.Expandable;
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
    private List<Expandable> mList;
    private CategoryAdapter adapter;
    private Context mContext;
    private String categories;
    private View progressLayout;

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
        progressLayout.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        mViewModel = new ViewModelProvider(this).get(ExpandableListViewModel.class);
        mViewModel.getProductCategoryApi();

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(Constants.EXPANDABLE_LIST);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        mList = new ArrayList<>();

        mViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), productCategory -> {
            if (productCategory != null) {
                progressLayout.setVisibility(View.GONE);
                mList.clear();
                productCategory.forEach(it -> {
                    mList.add(new Expandable(new ArrayList<Product>(), it.getName(), it.getId()));
                });
                adapter = new CategoryAdapter(mList, mViewModel, this, this, progressLayout);
                recyclerView.setAdapter(adapter);
            } else {
                progressLayout.setVisibility(View.GONE);
                HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.navigation_expandable_list);
            }
        });
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