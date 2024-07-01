package com.example.taskdemo.ui.tab.bookmark;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class BookmarkFragment extends Fragment {

    private BookmarkViewModel mViewModel;
    private FragmentBookmarkBinding binding;
    private RecyclerView recyclerView;
    private BookMarkAdapter mAdapter;
    private Context mContext;
    private ArrayList<String> stringArrayList = new ArrayList<>();

    public static BookmarkFragment newInstance() {
        return new BookmarkFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mContext = getContext();

        mViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);

        recyclerView = binding.bookmarkProductRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new BookMarkAdapter(stringArrayList);
        recyclerView.setAdapter(mAdapter);

        populateRecyclerView();
        enableSwipeToDeleteAndUndo();

        return root;
    }

    private void populateRecyclerView() {
        stringArrayList.add("Item 1");
        stringArrayList.add("Item 2");
        stringArrayList.add("Item 3");
        stringArrayList.add("Item 4");
        stringArrayList.add("Item 5");
        stringArrayList.add("Item 6");
        stringArrayList.add("Item 7");
        stringArrayList.add("Item 8");
        stringArrayList.add("Item 9");
        stringArrayList.add("Item 10");

        mAdapter.notifyDataSetChanged();
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(mContext) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final String item = mAdapter.getData().get(position);

                mAdapter.removeItem(position);

                Snackbar snackbar = Snackbar
                        .make(binding.coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
