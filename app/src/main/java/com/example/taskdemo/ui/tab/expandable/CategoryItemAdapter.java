package com.example.taskdemo.ui.tab.expandable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ItemCategoryDetailsBinding;
import com.example.taskdemo.model.expandable.CategoryProduct;
import com.example.taskdemo.model.expandable.CategoryProductData;
import com.example.taskdemo.productinterface.OnClickCategoryItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.NestedViewHolder> {

    private final CategoryProductData productList;
    private final OnClickCategoryItem onClickCategoryItem;
    private final String categoryName;


    public CategoryItemAdapter(CategoryProductData productList, OnClickCategoryItem onClickCategoryItem, String categoryName) {
        this.productList = productList != null ? productList : new CategoryProductData();
        this.onClickCategoryItem = onClickCategoryItem;
        this.categoryName = categoryName;
    }

    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryDetailsBinding binding = ItemCategoryDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryItemAdapter.NestedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {

        CategoryProduct product = productList.products.get(position);
        holder.binding.tvProductTitle.setText(product.getTitle());
        holder.binding.tvProductPrice.setText(String.valueOf(product.getPrice()));

        holder.binding.tvProductDescription.setText(product.getDescription());
        List<String> images = product.images;
        if (images != null && !images.isEmpty()) {
            Picasso.get()
                    .load(images.get(0))
                    .fit()
                    .placeholder(R.drawable.image)
                    .into(holder.binding.productImgView);
        }

        // Set click listener on the product details layout
        holder.binding.productDetailsLl.setOnClickListener(v -> {
            onClickCategoryItem.onItemClick(categoryName, productList, product.id);
        });
    }

    @Override
    public int getItemCount() {
        return productList.products != null ? productList.products.size() : 0;
    }

    public static class NestedViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryDetailsBinding binding;

        public NestedViewHolder(@NonNull ItemCategoryDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
