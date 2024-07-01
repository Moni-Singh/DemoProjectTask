package com.example.taskdemo.ui.tab.photo;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentPhotoBinding;
import com.example.taskdemo.gallerymodels.response.GalleryData;
import com.example.taskdemo.utils.HelperMethod;

import java.util.ArrayList;
import java.util.List;

public class PhotoFragment extends Fragment {


    private PhotoViewModel mViewModel;
    private FragmentPhotoBinding binding;
    private GalleryAdapter galleryAdapter;
    private List<GalleryData> galleryItemList = new ArrayList<>();
    private Context mContext;
    private boolean isLoading = false;
    private View progressLayout;
    private int currentPage = 1;
    public static PhotoFragment newInstance() {
        return new PhotoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPhotoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        progressLayout = binding.progressLayout.getRoot();
        progressLayout.setVisibility(View.VISIBLE);
        mViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);

        mViewModel.getGalleryDetailsList(currentPage); // Load first page of data

        // Set up RecyclerView
        galleryAdapter = new GalleryAdapter(galleryItemList);
        binding.rvGallery.setLayoutManager(new GridLayoutManager(mContext, 2));
        binding.rvGallery.setAdapter(galleryAdapter);

        SwipeRefreshLayout swipeRefreshLayout = binding.swipeRefreshlayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh action
                currentPage = 1;
                galleryItemList.clear();
                galleryAdapter.notifyDataSetChanged();
                mViewModel.getGalleryDetailsList(currentPage);
            }
        });

        binding.rvGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    isLoading = true;
                    currentPage++;
                    mViewModel.getGalleryDetailsList(currentPage); // Fetch next page of data
                }
            }
        });

        mViewModel.getGalleryResponseObserver().observe(getViewLifecycleOwner(), new Observer<List<GalleryData>>() {
            @Override
            public void onChanged(List<GalleryData> galleryData) {
                progressLayout.setVisibility(View.GONE);
                if (galleryData != null) {
                    galleryItemList.addAll(galleryData);
                    galleryAdapter.notifyDataSetChanged();
                    isLoading = false;
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
                }
            }
        });

        return root;
    }
}
