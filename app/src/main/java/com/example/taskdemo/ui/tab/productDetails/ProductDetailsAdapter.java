package com.example.taskdemo.ui.tab.productDetails;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.MainActivity;
import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ItemProductDetailsBinding;
import com.example.taskdemo.model.response.Product;
import com.example.taskdemo.utils.ApplicationSharedPreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Product> productDetailsList;




    public ProductDetailsAdapter (List<Product> productDetailsList){
        this.productDetailsList = productDetailsList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductDetailsBinding binding= ItemProductDetailsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductDetailsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    Product product = productDetailsList.get(position);
    ProductDetailsViewHolder productDetailsViewHolder = (ProductDetailsViewHolder) holder;
    productDetailsViewHolder.binding.productDescriptionTv.setText(product.getDescription());
        productDetailsViewHolder.binding.productTitleTv.setText(product.getTitle());
        productDetailsViewHolder.binding.productPriceTv.setText("Price: " + product.getPrice());
        Picasso.get()
                .load(product.getImage())
                .placeholder(R.drawable.image)
                .into(productDetailsViewHolder.binding.productImage);
        productDetailsViewHolder.binding.ratingtv.setText(String.format("%.1f", product.getRating().getRate()));


        boolean isLikedItem = MainActivity.likeProducts.contains(product.getId());
        if (isLikedItem) {
            productDetailsViewHolder.binding.likeProductDetailsIv.setImageResource(R.drawable.ic_like);
        } else {
            productDetailsViewHolder.binding.likeProductDetailsIv.setImageResource(R.drawable.ic_unlike);
        }

        productDetailsViewHolder.binding.likeProductDetailsIv.setOnClickListener(v -> {
            ImageView imageView = (ImageView) v;
            if (MainActivity.likeProducts.contains(product.getId())) {
                imageView.setImageResource(R.drawable.ic_unlike);
                MainActivity.likeProducts.remove(MainActivity.likeProducts.indexOf(product.getId()));
            }else{
                imageView.setImageResource(R.drawable.ic_like);
                MainActivity.likeProducts.add(product.getId());
            }
        });


        boolean isBookedItem = MainActivity.bookmarked.contains(product.getId());

        if (isBookedItem){
            productDetailsViewHolder.binding.bookmarkProductIv.setImageResource(R.drawable.ic_bookmark_add);
        }else {
            productDetailsViewHolder.binding.bookmarkProductIv.setImageResource(R.drawable.ic_bookmark);
        }

        productDetailsViewHolder.binding.bookmarkProductIv.setOnClickListener(v -> {
            ImageView imageView = (ImageView) v;
            if (MainActivity.bookmarked.contains(product.getId())) {
                imageView.setImageResource(R.drawable.ic_bookmark);
                MainActivity.bookmarked.remove(MainActivity.bookmarked.indexOf(product.getId()));
            }else{
                Product clickedProduct = productDetailsList.get(productDetailsViewHolder.getAdapterPosition());
                ApplicationSharedPreferences sharedPreferences = new ApplicationSharedPreferences(v.getContext());
                sharedPreferences.saveProduct(clickedProduct);
                imageView.setImageResource(R.drawable.ic_bookmark_add);
                MainActivity.bookmarked.add(product.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productDetailsList.size();
    }

    static class ProductDetailsViewHolder extends RecyclerView.ViewHolder{
        private final ItemProductDetailsBinding binding;

        public  ProductDetailsViewHolder(ItemProductDetailsBinding binding){
            super(binding.getRoot());

            this.binding = binding;
        }
    }

}
