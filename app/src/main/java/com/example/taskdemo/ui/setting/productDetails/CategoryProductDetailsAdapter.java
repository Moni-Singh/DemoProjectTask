package com.example.taskdemo.ui.setting.productDetails;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ItemCategoryProductDetailsBinding;

import com.example.taskdemo.model.category.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Product> productDetailsList;


    public CategoryProductDetailsAdapter(List<Product> productDetailsList) {
        this.productDetailsList = productDetailsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryProductDetailsBinding binding = ItemCategoryProductDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryProductDetailsAdapter.CategoryProductDetailsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = productDetailsList.get(position);
        CategoryProductDetailsAdapter.CategoryProductDetailsViewHolder categoryProductDetailsViewHolder = (CategoryProductDetailsAdapter.CategoryProductDetailsViewHolder) holder;
        categoryProductDetailsViewHolder.binding.productDescriptionTv.setText(product.getDescription());
        categoryProductDetailsViewHolder.binding.productTitleTv.setText(product.getTitle());
        categoryProductDetailsViewHolder.binding.productPriceTv.setText("Price: " + product.getPrice());

        String imageUrl = product.getImages().get(0);

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image)
                .into(categoryProductDetailsViewHolder.binding.productImage);
    }

    @Override
    public int getItemCount() {
        return productDetailsList.size();
    }

    static class CategoryProductDetailsViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryProductDetailsBinding binding;

        public CategoryProductDetailsViewHolder(ItemCategoryProductDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}