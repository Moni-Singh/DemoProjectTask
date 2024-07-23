package com.example.taskdemo.ui.setting.expandableList;

import static androidx.core.content.ContextCompat.getString;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.MainActivity;
import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ItemCategoryBinding;
import com.example.taskdemo.model.category.CategoryModel;
import com.example.taskdemo.model.category.Product;
import com.example.taskdemo.productinterface.OnClickCategoryProduct;
import com.example.taskdemo.utils.HelperMethod;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemViewHolder> {

    private List<CategoryModel> categoryList;
    private int expandedPosition = -1;
    private ExpandableListViewModel mViewModel;
    private LifecycleOwner mLifecycleOwner;
    private View progressLayout;
    private Context mContext;
    private OnClickCategoryProduct onClickCategoryProduct;

    public CategoryAdapter(List<CategoryModel> mList, ExpandableListViewModel expandableListViewModel, LifecycleOwner lifecycleOwner, OnClickCategoryProduct onClickCategoryProduct, View progressLayout, Context mContext) {
        this.onClickCategoryProduct = onClickCategoryProduct;
        this.categoryList = mList;
        this.mViewModel = expandableListViewModel;
        this.mLifecycleOwner = lifecycleOwner;
        this.progressLayout = progressLayout;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        CategoryModel model = categoryList.get(position);
        holder.binding.categoryTv.setText(model.getCategoryName());

        boolean isExpandable = (position == MainActivity.getExpandedPosition());
        holder.binding.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable) {
            holder.binding.arroImageview.setImageResource(R.drawable.ic_arrow_up);
            List<Product> productList = model.getCategoryProduct();
            if (productList == null || productList.isEmpty()) {
                int categoryId = model.getCategoryId();
                mViewModel.getCategoryProducts(categoryId);
                mViewModel.getPoductCategoryLiveData().observe(mLifecycleOwner, productCategoryItem -> {
                    holder.binding.progressLayout.getRoot().setVisibility(View.GONE);
                    if (productCategoryItem != null) {
                        categoryList.get(position).setCategoryProduct(productCategoryItem);
                        CategoryItemAdapter adapter = new CategoryItemAdapter(productCategoryItem, onClickCategoryProduct, categoryId);
                        holder.binding.categoryProductRv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
                        holder.binding.categoryProductRv.setHasFixedSize(true);
                        holder.binding.categoryProductRv.setAdapter(adapter);
                    }
                });
            } else {
                CategoryItemAdapter adapter = new CategoryItemAdapter(productList, onClickCategoryProduct, model.getCategoryId());
                holder.binding.categoryProductRv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
                holder.binding.categoryProductRv.setHasFixedSize(true);
                holder.binding.categoryProductRv.setAdapter(adapter);
            }
        } else {
            holder.binding.arroImageview.setImageResource(R.drawable.ic_arrow_down);
        }

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
                int categoryId = model.getCategoryId();
                mViewModel.getCategoryProducts(categoryId);
                mViewModel.getPoductCategoryLiveData().observe(mLifecycleOwner, productCategoryItem -> {
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

        private final ItemCategoryBinding binding;
        public ItemViewHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
