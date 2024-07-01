package com.example.taskdemo.ui.tab.discover;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskdemo.MainActivity;
import com.example.taskdemo.R;

import com.example.taskdemo.databinding.ItemHeaderBinding;
import com.example.taskdemo.databinding.ItemProductsBinding;

import com.example.taskdemo.databinding.ItemSliderBinding;
import com.example.taskdemo.model.response.Product;
import com.example.taskdemo.productinterface.OnProductItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StickyHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Product> productList = new ArrayList<>();
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_SLIDER = 2;
    OnProductItemClickListener onProductItemClickListener;

    public StickyHeaderAdapter(OnProductItemClickListener onProductItemClickListener) {
        this.onProductItemClickListener = onProductItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_HEADER){
            ItemHeaderBinding binding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new HeaderViewHolder(binding);
        } else if (viewType == VIEW_TYPE_SLIDER) {
            ItemSliderBinding binding= ItemSliderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new SliderViewHolder(binding);
        } else{
            ItemProductsBinding binding = ItemProductsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProductViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = productList.get(position);
        if(product.isCategory()){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.binding.tvHeader.setText(product.getCategory());
        } else if (product.isSlider()) {
            SliderViewHolder sliderViewHolder = (SliderViewHolder) holder;
            ProductsAdapter productsAdapter = new ProductsAdapter(product.getProductList(), onProductItemClickListener);
            sliderViewHolder.binding.sliderImageVp.setAdapter(productsAdapter);
            sliderViewHolder.binding.pagerDots.setVisibility(View.VISIBLE);
            productsAdapter.setupPagerDots(sliderViewHolder.binding.pagerDots, 0);

            sliderViewHolder.binding.sliderImageVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);

                    productsAdapter.setupPagerDots(sliderViewHolder.binding.pagerDots, position);
                }
            });
        } else{
            ProductViewHolder productHolder = (ProductViewHolder) holder;
            productHolder.binding.tvProductTitle.setText(product.getTitle());
            productHolder.binding.tvProductPrice.setText("Price: " + product.getPrice());
            productHolder.binding.tvProductDescription.setText(product.getDescription());
            Picasso.get().load(product.getImage()).fit().into(productHolder.binding.productImgView);
            productHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onProductItemClickListener.onItemClick(product.getId(),product);
                }
            });

            boolean isLikedItem = MainActivity.likeProducts.contains(product.getId());
            if (isLikedItem) {
                productHolder.binding.likeProductImageView.setImageResource(R.drawable.ic_like);
            } else {
                productHolder.binding.likeProductImageView.setImageResource(R.drawable.ic_unlike);
            }

            productHolder.binding.likeProductImageView.setOnClickListener(v -> {
                ImageView imageView = (ImageView) v;
                if (MainActivity.likeProducts.contains(product.getId())) {
                    imageView.setImageResource(R.drawable.ic_unlike);
                    MainActivity.likeProducts.remove(MainActivity.likeProducts.indexOf(product.getId()));
                }else{
                    imageView.setImageResource(R.drawable.ic_like);
                    MainActivity.likeProducts.add(product.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    @Override
    public int getItemViewType(int position) {
        Product product = productList.get(position);
        if(product.isCategory()){
            return VIEW_TYPE_HEADER;
        } else if (product.isSlider()) {
            return  VIEW_TYPE_SLIDER;
        }
        return VIEW_TYPE_ITEM;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductsBinding binding;

        public ProductViewHolder(ItemProductsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final ItemHeaderBinding binding;

        public HeaderViewHolder(ItemHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    class SliderViewHolder extends RecyclerView.ViewHolder {
        private final ItemSliderBinding binding;

        public SliderViewHolder(ItemSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }




}
