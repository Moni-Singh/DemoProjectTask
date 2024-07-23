package com.example.taskdemo.ui.tab.expandable.productDetails;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ItemCategoryProductDetailsBinding;
import com.example.taskdemo.model.expandable.CategoryProduct;
import com.example.taskdemo.model.expandable.CategoryProductData;
import com.squareup.picasso.Picasso;

public class CategoryProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private CategoryProductData productDetailsList;

    public CategoryProductDetailsAdapter(CategoryProductData productDetailsList) {
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
        CategoryProduct categoryProduct = productDetailsList.products.get(position);
        CategoryProductDetailsAdapter.CategoryProductDetailsViewHolder categoryProductDetailsViewHolder = (CategoryProductDetailsAdapter.CategoryProductDetailsViewHolder) holder;
        categoryProductDetailsViewHolder.binding.productDescriptionTv.setText(categoryProduct.getDescription());
        categoryProductDetailsViewHolder.binding.productTitleTv.setText(categoryProduct.getTitle());
        categoryProductDetailsViewHolder.binding.productPriceTv.setText(String.valueOf(categoryProduct.getPrice()));
        String imageUrl = categoryProduct.getImages().get(0);

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image)
                .into(categoryProductDetailsViewHolder.binding.productImage);
    }

    @Override
    public int getItemCount() {
        return productDetailsList.products != null ? productDetailsList.products.size() : 0;
    }

    static class CategoryProductDetailsViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryProductDetailsBinding binding;

        public CategoryProductDetailsViewHolder(ItemCategoryProductDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}