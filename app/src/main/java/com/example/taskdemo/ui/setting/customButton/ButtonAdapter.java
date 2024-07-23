package com.example.taskdemo.ui.setting.customButton;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.databinding.ItemCustomButtonBinding;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    private final List<Integer> buttonNumbers;
    private final Context context;

    public ButtonAdapter(Context context, List<Integer> buttonNumbers) {
        this.context = context;
        this.buttonNumbers = buttonNumbers;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomButtonBinding binding = ItemCustomButtonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ButtonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        int number = buttonNumbers.get(position);
        holder.binding.btnCustom.setText("Button " + number);

    }

    @Override
    public int getItemCount() {
        return buttonNumbers.size();
    }

    static class ButtonViewHolder extends RecyclerView.ViewHolder {

        private ItemCustomButtonBinding binding;

        ButtonViewHolder(ItemCustomButtonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
