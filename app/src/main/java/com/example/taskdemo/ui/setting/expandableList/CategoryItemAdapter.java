package com.example.taskdemo.ui.setting.expandableList;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.R;

import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.NestedViewHolder> {

        private List<String> mList;

        public CategoryItemAdapter(List<String> mList){
            this.mList = mList;
        }
        @NonNull
        @Override
        public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_item , parent , false);
            return new NestedViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
            holder.mTv.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class NestedViewHolder extends RecyclerView.ViewHolder{
            private TextView mTv;
            public NestedViewHolder(@NonNull View itemView) {
                super(itemView);
                mTv = itemView.findViewById(R.id.nestedItemTv);
            }
        }
    }