package com.example.taskdemo.ui.tab.expandable;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.MainActivity;
import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ItemProductCategoryBinding;
import com.example.taskdemo.model.expandable.CategoryExpandableModel;
import com.example.taskdemo.model.expandable.CategoryProductData;
import com.example.taskdemo.productinterface.OnClickCategoryItem;
import com.example.taskdemo.utils.HelperMethod;
import com.google.gson.Gson;

import java.util.List;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ItemViewHolder> {

    private List<CategoryExpandableModel> categoryList;
    private int expandedPosition = -1;
    private ExpandableViewModel mViewModel;
    private LifecycleOwner mLifecycleOwner;
    private Context mContext;
    private OnClickCategoryItem onClickCategoryItem;

    public ProductCategoryAdapter(List<CategoryExpandableModel> mList, ExpandableViewModel expandableListViewModel, LifecycleOwner lifecycleOwner, OnClickCategoryItem onClickCategoryItem, Context mContext) {
        this.onClickCategoryItem = onClickCategoryItem;
        this.categoryList = mList;
        this.mViewModel = expandableListViewModel;
        this.mLifecycleOwner = lifecycleOwner;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProductCategoryAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductCategoryBinding binding = ItemProductCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductCategoryAdapter.ItemViewHolder(binding);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull ProductCategoryAdapter.ItemViewHolder holder, int position) {
        CategoryExpandableModel model = categoryList.get(position);
        holder.binding.categoryTv.setText(model.getCategoryName());

        // Determine if the current item is expanded
        boolean isExpandable = (position == MainActivity.getExpandedPosition());
        holder.binding.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        // Set arrow image based on expansion state
        if (isExpandable) {
            holder.binding.arroImageview.setImageResource(R.drawable.ic_arrow_up);

            // Check if category products are already loaded
           CategoryProductData productList = model.getCategoryProduct();
            if (productList == null || productList.products == null || productList.products.isEmpty()) {

                // Fetch products for the category if not already loaded
                String categoryName = model.getCategorySlug();
                mViewModel.getProductByCategory(categoryName);
                mViewModel.getCategoryProductLiveData().observe(mLifecycleOwner, productCategoryItem -> {
                    holder.binding.progressLayout.getRoot().setVisibility(View.GONE);
                    if (productCategoryItem != null) {
                        // Set up adapter for category products
                        categoryList.get(position).setCategoryProduct(productCategoryItem);
                         com.example.taskdemo.ui.tab.expandable.CategoryItemAdapter adapter = new com.example.taskdemo.ui.tab.expandable.CategoryItemAdapter(productCategoryItem, onClickCategoryItem, categoryName);
                        holder.binding.categoryProductRv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
                        holder.binding.categoryProductRv.setHasFixedSize(true);
                        holder.binding.categoryProductRv.setAdapter(adapter);
                    }
                });
            } else {
                com.example.taskdemo.ui.tab.expandable.CategoryItemAdapter adapter = new CategoryItemAdapter(productList, onClickCategoryItem,model.getCategorySlug());
                holder.binding.categoryProductRv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
                holder.binding.categoryProductRv.setHasFixedSize(true);
                holder.binding.categoryProductRv.setAdapter(adapter);
            }
        } else {
            holder.binding.arroImageview.setImageResource(R.drawable.ic_arrow_down);
        }
        // Handle click event to expand or collapse the category
        holder.binding.linearLayout.setOnClickListener(v -> {
            holder.binding.progressLayout.getRoot().setVisibility(View.VISIBLE);
            int oldPosition = MainActivity.getExpandedPosition();
            int newPosition = (position == oldPosition) ? -1 : position;
            MainActivity.setExpandedPosition(newPosition);

            if (oldPosition != -1) {
                notifyItemChanged(oldPosition);
            }
            notifyItemChanged(newPosition);

            if (newPosition != -1) {
                String categoryName= model.getCategorySlug();
                mViewModel.getProductByCategory(categoryName);
                mViewModel.getCategoryProductLiveData().observe(mLifecycleOwner, productCategoryItem -> {
                    holder.binding.progressLayout.getRoot().setVisibility(View.GONE);
                    if (productCategoryItem != null) {
                        categoryList.get(position).setCategoryProduct(productCategoryItem);
                        notifyItemChanged(newPosition);
                    } else {
                        HelperMethod.showToast(mContext.getString(R.string.something_went_wrong),mContext);
                        holder.binding.progressLayout.getRoot().setVisibility(View.GONE);
                    }
                });
            } else {
                holder.binding.progressLayout.getRoot().setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemProductCategoryBinding binding;
        public ItemViewHolder(@NonNull ItemProductCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}