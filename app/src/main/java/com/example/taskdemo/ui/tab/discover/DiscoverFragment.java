package com.example.taskdemo.ui.tab.discover;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taskdemo.MainActivity;
import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentDiscoverBinding;
import com.example.taskdemo.model.response.Product;
import com.example.taskdemo.productinterface.OnClickLocation;
import com.example.taskdemo.productinterface.OnProductItemClickListener;
import com.example.taskdemo.utils.Constants;
import com.example.taskdemo.utils.HelperMethod;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment implements OnProductItemClickListener, OnClickLocation {

    private DiscoverViewModel mViewModel;
    private FragmentDiscoverBinding binding;
    private Context mContext;
    private MainActivity mainActivity;
    private StickyHeaderItemDecoration stickyHeaderItemDecoration;
    private List<Product> sliderProducts = new ArrayList<>();
    private View progressLayout;
    private int productId;
    private StickyHeaderAdapter stickyHeaderAdapter = new StickyHeaderAdapter(this, this);
    private List<Product> products = new ArrayList<>();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 10001;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng eventLocation = new LatLng(23.0384903236922, 72.51296554003244);  // Event location
    private float geofenceRadius = 3000; // Radius of the geofence
    private boolean isLocationRequested = false;
    private boolean isUserInitiatedLocationRequest = false; // Flag to track user-initiated location requests

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mContext = getContext();

        progressLayout = binding.progressLayout.getRoot();
        mViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        setupRecyclerView();
        observeViewModel();
        mainActivity = (MainActivity) getActivity();
        products.clear();
        progressLayout.setVisibility(View.VISIBLE);
        mViewModel.getProductByLimit();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);

        // Check for location services and request location updates if enabled
        if (!isLocationRequested && isLocationEnabled()) {
            requestLocationUpdates(productId, false); // Not user-initiated
        }

        return root;
    }

    private void refreshData() {
        if (mainActivity != null) {
            stickyHeaderAdapter.notifyDataSetChanged();
        }
    }

    private void observeViewModel() {
        mViewModel.getProductCategoryLiveData().observe(getViewLifecycleOwner(), productCategory -> {
            if (productCategory != null) {

                progressLayout.setVisibility(View.GONE);
                products.clear();
                Product sliderProduct = new Product();
                sliderProduct.setSlider(true);
                sliderProduct.setProductList(sliderProducts);
                products.add(sliderProduct);
                productCategory.forEach((k, v) -> {
                    Product product = new Product();
                    product.setCategory(k);
                    product.setCategory(true);
                    products.add(product);
                    products.addAll(v);
                });
                stickyHeaderItemDecoration.setProductList(products);
                stickyHeaderAdapter.setProductList(products);
            } else {
                progressLayout.setVisibility(View.GONE);
                HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
            }
        });

        mViewModel.getProductsByLimitLiveData().observe(getViewLifecycleOwner(), sliderProducts -> {
            if (sliderProducts != null) {
                this.sliderProducts = sliderProducts;
                mViewModel.getProductsApi();
            } else {
                HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
            }
        });
    }

    private void setupRecyclerView() {
        binding.displayProductRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.displayProductRv.setAdapter(stickyHeaderAdapter);
        stickyHeaderItemDecoration = new StickyHeaderItemDecoration(R.layout.item_header);
        binding.displayProductRv.addItemDecoration(stickyHeaderItemDecoration);
    }

    //OnClick to navigate in details screen
    @Override
    public void onItemClick(int id, Product product) {
        if (product != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.PRODUCT_DETAILS, product);
            bundle.putInt(Constants.PRODUCT_ID, id);
            Navigation.findNavController(requireView()).navigate(R.id.navigation_productDetails, bundle);
        } else {
            HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
        }
    }

    //OnClick to check event location
    @Override
    public void onItemClick(int id) {
        this.productId = id;
        isUserInitiatedLocationRequest = true;
        // Check if location services are enabled
        if (!isLocationEnabled()) {
            new AlertDialog.Builder(mContext)
                    .setTitle(R.string.location_services_disabled)
                    .setMessage(R.string.please_enable_location_services)
                    .setPositiveButton(R.string.settings, (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
            // Request location permission if not granted
        } else if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Request location updates if permission is granted
            requestLocationUpdates(id, true); // User-initiated
        }
    }

    // Checks if location services are enabled on the device
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // Requests location updates from the fused location provider
    @SuppressLint("MissingPermission")
    private void requestLocationUpdates(int id, boolean isUserInitiated) {
        isLocationRequested = true;
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult.getLastLocation() != null) {
                    Location location = locationResult.getLastLocation();
                    processLocation(location, id, isUserInitiated);
                    fusedLocationClient.removeLocationUpdates(this); // Stop location updates
                } else {
                    HelperMethod.showToast(getString(R.string.unable_to_get_location), mContext);
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if (!locationAvailability.isLocationAvailable()) {
                    HelperMethod.showToast(getString(R.string.location_service_not_available), mContext);
                }
            }
        };

        // Request location updates
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    // Processes the location data to determine if the user is within the event's geofence
    private void processLocation(Location location, int id, boolean isUserInitiated) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        float[] results = new float[1];
        Location.distanceBetween(userLocation.latitude, userLocation.longitude,
                eventLocation.latitude, eventLocation.longitude, results);
        float distance = results[0];

        if (distance < geofenceRadius) {
            if (!MainActivity.eventLocation.contains(id)) {
                MainActivity.eventLocation.add(id);
            }
            refreshData();
            if (isUserInitiated) {
                showEventAlertDialog();
                HelperMethod.showToast(getString(R.string.you_are_at_location), mContext);
            }
        } else {
            if (MainActivity.eventLocation.contains(id)) {
                MainActivity.eventLocation.remove(Integer.valueOf(id)); // Safely remove by value
            }
            refreshData();
            if (isUserInitiated) {
                showDistanceAlertDialog(distance);
            }
        }
        isUserInitiatedLocationRequest = false;
    }

    // Shows an alert dialog indicating the user is at the event location
    private void showEventAlertDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.event_location)
                .setMessage(R.string.you_are_at_location)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    // Shows an alert dialog indicating the user is too far from the event location
    private void showDistanceAlertDialog(float distance) {
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.you_are_to_far)
                .setMessage("You are " + String.format("%.2f", distance) + " meters away from the event location.")
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    // Handles the result of location permission requests
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates(productId, true); // User-initiated
            } else {
                HelperMethod.showToast(getString(R.string.check_location_pemrission), mContext);
            }
        }
    }
}
