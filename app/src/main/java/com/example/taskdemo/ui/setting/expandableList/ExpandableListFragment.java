package com.example.taskdemo.ui.setting.expandableList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentExpandableListBinding;
import com.example.taskdemo.model.category.Expandable;
import com.example.taskdemo.ui.tab.bookmark.BookmarkViewModel;
import com.example.taskdemo.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpandableListFragment extends Fragment {

    private ExpandableListViewModel mViewModel;
    private FragmentExpandableListBinding binding;

    private RecyclerView recyclerView;
    private List<Expandable> mList;
    private CategoryAdapter adapter;
    private String categories;
    public static ExpandableListFragment newInstance() {
        return new ExpandableListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentExpandableListBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        recyclerView = binding.mainRecyclervie;
        recyclerView.setHasFixedSize(true);
        mViewModel = new ViewModelProvider(this).get(ExpandableListViewModel.class);
        mViewModel.getProductCategoryApi();
        mViewModel.getCategoryProducts();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(Constants.EXPANDABLE_LIST);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        mList = new ArrayList<>();

        mViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), productCategory -> {
            if(productCategory != null){
                Log.d("productCategory", productCategory.toString());
                productCategory.forEach((k, v) -> {
                    Log.d("productCategory", k);
                    this.categories = k;

                });



                adapter.notifyDataSetChanged();
            }
        });

        mViewModel.getPoductCategoryLiveData().observe(getViewLifecycleOwner(),productCategoryItem ->{
            if(productCategoryItem !=null){
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(productCategoryItem);
                Log.d("productCategoryItem", jsonResponse);
            }
        });

        List<String> nestedList1 = new ArrayList<>();
        nestedList1.add("Jams and Honey");
        nestedList1.add("Pickles and Chutneys");
        nestedList1.add("Readymade Meals");

        List<String> nestedList6 = new ArrayList<>();
        nestedList6.add("Pasta");
        nestedList6.add("Spices");

        mList.add(new Expandable(nestedList1 , "Instant Food and Noodles"));
        mList.add(new Expandable(nestedList6,"Baby Care"));

        adapter = new CategoryAdapter(mList);
        recyclerView.setAdapter(adapter);
        return  root;
    }
}