package com.example.taskdemo.ui.tab.photo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.taskdemo.R;
import com.example.taskdemo.databinding.ItemGalleryPhotoBinding;
import com.example.taskdemo.gallerymodels.response.GalleryData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<GalleryData> galleryItems;

    public GalleryAdapter(List<GalleryData> galleryItems) {
        this.galleryItems = galleryItems;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemGalleryPhotoBinding binding = ItemGalleryPhotoBinding.inflate(layoutInflater, parent, false);
        return new GalleryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        GalleryData item = galleryItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return galleryItems.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        private final ItemGalleryPhotoBinding binding;

        public GalleryViewHolder(ItemGalleryPhotoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(GalleryData item) {
            Picasso.get()
                    .load(item.getUrls().getRaw())
                    .placeholder(R.drawable.nature)
                    .fit()
                    .into(binding.ivgalleryPhoto);
        }
    }
}
