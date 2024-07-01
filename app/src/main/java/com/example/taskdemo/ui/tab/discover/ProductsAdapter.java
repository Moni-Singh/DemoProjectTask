package com.example.taskdemo.ui.tab.discover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ItemSlideImageBinding;
import com.example.taskdemo.model.response.Product;
import com.example.taskdemo.productinterface.OnProductItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Product> productList;
    private OnProductItemClickListener onProductItemClickListener;


    public ProductsAdapter(List<Product> productList, OnProductItemClickListener onProductItemClickListener ) {
        this.productList = productList;
        this.onProductItemClickListener = onProductItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSlideImageBinding binding = ItemSlideImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductSliderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = productList.get(position);
        ProductSliderViewHolder productSliderViewHolder = (ProductSliderViewHolder) holder;
        Picasso.get().load(product.getImage()).fit().into(productSliderViewHolder.binding.sliderImage);
        setupPagerDots(productSliderViewHolder.binding.pagerDots, position);
    }

    // Method to setup pager dots for slider items
    private void setupPagerDots(LinearLayout pagerDotsContainer, int position) {
        // Clear existing dots
        pagerDotsContainer.removeAllViews();

        Context context = pagerDotsContainer.getContext();
        for (int i = 0; i < productList.size(); i++) {
            ImageView dot = new ImageView(context);
            dot.setImageDrawable(ContextCompat.getDrawable(context, i == position ? R.drawable.active_dot : R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            pagerDotsContainer.addView(dot);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    static class ProductSliderViewHolder extends RecyclerView.ViewHolder {
        private final ItemSlideImageBinding binding;

        public ProductSliderViewHolder(ItemSlideImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
