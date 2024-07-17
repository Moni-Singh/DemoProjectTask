package com.example.taskdemo.ui.setting.expandableList;

import static android.provider.Settings.System.getString;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ItemCategoryBinding;
import com.example.taskdemo.model.category.Expandable;
import com.example.taskdemo.model.category.Product;
import com.example.taskdemo.productinterface.OnClickCategoryProduct;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemViewHolder> {

    private List<Expandable> mList;
    private int expandedPosition = -1;
    private ExpandableListViewModel mViewModel;
    private LifecycleOwner mLifecycleOwner;
    private View progressLayout;
    private OnClickCategoryProduct onClickCategoryProduct;

    public CategoryAdapter(List<Expandable> mList, ExpandableListViewModel expandableListViewModel, LifecycleOwner lifecycleOwner,OnClickCategoryProduct onClickCategoryProduct, View progressLayout) {
        this.onClickCategoryProduct = onClickCategoryProduct;
        this.mList = mList;
        this.mViewModel = expandableListViewModel;
        this.mLifecycleOwner = lifecycleOwner;
        this.progressLayout = progressLayout;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Expandable model = mList.get(position);
        holder.binding.categoryTv.setText(model.getCategoryName());
        boolean isExpandable = (position == expandedPosition);
        holder.binding.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable) {
            holder.binding.arroImageview.setImageResource(R.drawable.ic_arrow_up);
            List<Product> list = model.getCategoryProduct();
            int categoryId = model.getCategoryId();
            CategoryItemAdapter adapter = new CategoryItemAdapter(list,onClickCategoryProduct,categoryId);
            holder.binding.categoryProductRv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            holder.binding.categoryProductRv.setHasFixedSize(true);
            holder.binding.categoryProductRv.setAdapter(adapter);
        } else {
            holder.binding.arroImageview.setImageResource(R.drawable.ic_arrow_down);
        }

        holder.binding.linearLayout.setOnClickListener(v -> {

            holder.binding.progressLayout.getRoot().setVisibility(View.VISIBLE);

            int oldPosition = expandedPosition;
            expandedPosition = (position == expandedPosition) ? -1 : position;

            if (expandedPosition != -1) {
                int categoryId = model.getCategoryId();
                mViewModel.getCategoryProducts(categoryId);
                mViewModel.getPoductCategoryLiveData().observe(mLifecycleOwner, productCategoryItem -> {
                    if (productCategoryItem != null) {
                        holder.binding.progressLayout.getRoot().setVisibility(View.GONE);
                        mList.get(position).setCategoryProduct(productCategoryItem);
                        notifyItemChanged(expandedPosition);
                    }else{
                        holder.binding.progressLayout.getRoot().setVisibility(View.GONE);
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

        private final ItemCategoryBinding binding;
        public ItemViewHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
