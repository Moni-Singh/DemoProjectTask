package com.example.taskdemo.ui.TabLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentMainTabBinding;
import com.example.taskdemo.utils.Constants;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

public class TabMainFragment extends Fragment {
    private FragmentMainTabBinding binding;
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MenuHost menuHost = requireActivity();

        tabLayout = binding.tabLayout;
        toolbar = root.findViewById(R.id.mToolbar);
        viewPager = binding.tabViewpager;
        collapsingToolbarLayout = root.findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = root.findViewById(R.id.appBar);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("");
        setHasOptionsMenu(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Product");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText(Constants.DISCOVER));
        tabLayout.addTab(tabLayout.newTab().setText(Constants.PHOTO));
        tabLayout.addTab(tabLayout.newTab().setText(Constants.BOOKMARK));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.getTabAt(0).select();

        final TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(requireContext(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabLayoutAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_settings) {
                    // Handle settings action
                }
                return true;
            }
        }, getViewLifecycleOwner());
        return root;
    }
}
