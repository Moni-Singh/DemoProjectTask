package com.example.taskdemo.ui.TabLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
    private View bottomNavigation;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentMainTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize bottom navigation view
        bottomNavigation = binding.bottomNavigation.getRoot();

        // Set up menu host to manage menu items
        MenuHost menuHost = requireActivity();

        // Configure window settings for status bar color
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.red));

        // Initialize views
        tabLayout = binding.tabLayout;
        toolbar = root.findViewById(R.id.mToolbar);
        viewPager = binding.tabViewpager;
        collapsingToolbarLayout = root.findViewById(R.id.collapsing_toolbar);

        // Set up the toolbar
        AppBarLayout appBarLayout = root.findViewById(R.id.appBar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        setHasOptionsMenu(true);

        // Add offset change listener to handle toolbar collapse and expansion
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @SuppressLint("ResourceAsColor")
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.product));
                    bottomNavigation.setVisibility(View.GONE);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    bottomNavigation.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });

        // Set up tabs in TabLayout
        tabLayout.addTab(tabLayout.newTab().setText(Constants.DISCOVER));
        tabLayout.addTab(tabLayout.newTab().setText(Constants.CATEGORY));
        tabLayout.addTab(tabLayout.newTab().setText(Constants.BOOKMARK));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.getTabAt(0).select();

        // Set up ViewPager with TabLayoutAdapter
        final TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(requireContext(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabLayoutAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Add tab selected listener to handle tab selection
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

        // Add menu provider to handle options menu creation and item selection
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_settings) {
                    Navigation.findNavController(requireView()).navigate(R.id.navigation_Setting);
                }
                return true;
            }
        }, getViewLifecycleOwner());
        return root;
    }
}
