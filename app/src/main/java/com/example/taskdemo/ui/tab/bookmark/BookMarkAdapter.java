package com.example.taskdemo.ui.tab.bookmark;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ItemBookmarkProductBinding;
import com.example.taskdemo.model.response.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.MyViewHolder> {

    private ArrayList<Product> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemBookmarkProductBinding binding;

        public MyViewHolder(ItemBookmarkProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public BookMarkAdapter(ArrayList<Product> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookmarkProductBinding binding = ItemBookmarkProductBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = data.get(position);
        holder.binding.tvProductDescription.setText(product.getDescription());
        holder.binding.tvProductTitle.setText(product.getTitle());
        Picasso.get()
                .load(product.getImage())
                .placeholder(R.drawable.image)
                .fit()
                .into(holder.binding.productImgView);
        holder.binding.tvProductPrice.setText("Price: " + product.getPrice());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Product item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Product> getData() {
        return data;
    }
}
