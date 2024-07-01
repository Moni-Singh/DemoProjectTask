package com.example.taskdemo.ui.tab.bookmark;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.databinding.FragmentBookmarkBinding;
import com.example.taskdemo.model.response.Product;
import com.example.taskdemo.utils.ApplicationSharedPreferences;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Set;

public class BookmarkFragment extends Fragment {

    private BookmarkViewModel mViewModel;
    private FragmentBookmarkBinding binding;
    private BookMarkAdapter mAdapter;
    private Context mContext;
    private View progressLayout;
    private ArrayList<Product> bookmarkedProductsList = new ArrayList<>();
    private ApplicationSharedPreferences sharedPreferences;

    public static BookmarkFragment newInstance() {
        return new BookmarkFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mContext = getContext();
        progressLayout = binding.progressLayout.getRoot();
        mViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        progressLayout.setVisibility(View.VISIBLE);
        sharedPreferences = new ApplicationSharedPreferences(getActivity());
        populateRecyclerView();
        enableSwipeToDeleteAndUndo();
        return root;
    }

    private void populateRecyclerView() {
        Set<Product> bookmarkedProducts = sharedPreferences.getAllBookmarkedProducts();
        bookmarkedProductsList.clear();
        bookmarkedProductsList.addAll(bookmarkedProducts);
        binding.bookmarkProductRv.setLayoutManager(new LinearLayoutManager(mContext));

        mAdapter = new BookMarkAdapter(bookmarkedProductsList);
        binding.bookmarkProductRv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        progressLayout.setVisibility(View.GONE);
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(mContext) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final Product item = mAdapter.getData().get(position);

                // Remove the product from SharedPreferences and RecyclerView
                mAdapter.removeItem(position);
                sharedPreferences.removeBookmarkedProduct(item);

                Snackbar snackbar = Snackbar
                        .make(binding.coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAdapter.restoreItem(item, position);
                        sharedPreferences.addBookmarkedProduct(item);
                        binding.bookmarkProductRv.scrollToPosition(position);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(binding.bookmarkProductRv);
    }
}
