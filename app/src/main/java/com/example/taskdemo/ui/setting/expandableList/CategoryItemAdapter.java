package com.example.taskdemo.ui.setting.expandableList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ItemCategoryProductItemBinding;
import com.example.taskdemo.model.category.Product;
import com.example.taskdemo.productinterface.OnClickCategoryProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.NestedViewHolder> {

    private List<Product> mList;
    private OnClickCategoryProduct onClickCategoryProduct;
    private int categoryId;


    public CategoryItemAdapter(List<Product> mList, OnClickCategoryProduct onClickCategoryProduct, int categoryId) {
        this.mList = mList;
        this.onClickCategoryProduct = onClickCategoryProduct;
        this.categoryId = categoryId;
    }

    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryProductItemBinding binding = ItemCategoryProductItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NestedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        Product product = mList.get(position);
        holder.binding.tvProductTitle.setText(product.getTitle());
        holder.binding.tvProductPrice.setText(String.valueOf(product.getPrice()));

        holder.binding.tvProductDescription.setText(product.getDescription());
        List<String> images = product.getImages();
        if (images != null && !images.isEmpty()) {
            Picasso.get()
                    .load(images.get(0))
                    .fit()
                    .placeholder(R.drawable.clothes)
                    .into(holder.binding.productImgView);
        }
        holder.binding.productDetailsLl.setOnClickListener(v -> {
            onClickCategoryProduct.onItemClick(categoryId, product, product.getId());
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class NestedViewHolder extends RecyclerView.ViewHolder {

        private ItemCategoryProductItemBinding binding;

        public NestedViewHolder(@NonNull ItemCategoryProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
