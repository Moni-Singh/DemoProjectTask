package com.example.taskdemo.ui.setting.expandableList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.R;
import com.example.taskdemo.model.category.Expandable;
import com.example.taskdemo.model.category.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemViewHolder> {

    private List<Expandable> mList;
    private int expandedPosition = -1;
    private ExpandableListViewModel mViewModel;
    private LifecycleOwner mLifecycleOwner;

    public CategoryAdapter(List<Expandable> mList, ExpandableListViewModel expandableListViewModel, LifecycleOwner lifecycleOwner) {
        this.mList = mList;
        this.mViewModel = expandableListViewModel;
        this.mLifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Expandable model = mList.get(position);
        holder.mTextView.setText(model.getCategoryName());
        Log.d("CategoryAdapter", model.getCategoryName());

        boolean isExpandable = (position == expandedPosition);
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable) {
            holder.mArrowImage.setImageResource(R.drawable.ic_arrow_up);
            List<Product> list = model.getCategoryProduct();
            CategoryItemAdapter adapter = new CategoryItemAdapter(list);
            holder.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            holder.nestedRecyclerView.setHasFixedSize(true);
            holder.nestedRecyclerView.setAdapter(adapter);
        } else {
            holder.mArrowImage.setImageResource(R.drawable.ic_arrow_down);
        }

        holder.linearLayout.setOnClickListener(v -> {

            int oldPosition = expandedPosition;
            expandedPosition = (position == expandedPosition) ? -1 : position;

            if (expandedPosition != -1) {
                int categoryId = model.getCategoryId(); // Get the category ID
                mViewModel.getCategoryProducts(categoryId);
//                mViewModel.getCategoryProducts();
                mViewModel.getPoductCategoryLiveData().observe(mLifecycleOwner, productCategoryItem -> {
                    if (productCategoryItem != null) {
                        mList.get(position).setCategoryProduct(productCategoryItem);
                        notifyItemChanged(expandedPosition);
                    }
                });
            }

            notifyItemChanged(oldPosition);
            notifyItemChanged(expandedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private RelativeLayout expandableLayout;
        private TextView mTextView;
        private ImageView mArrowImage;
        private RecyclerView nestedRecyclerView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            mTextView = itemView.findViewById(R.id.itemTv);
            mArrowImage = itemView.findViewById(R.id.arro_imageview);
            nestedRecyclerView = itemView.findViewById(R.id.child_rv);
        }
    }
}
