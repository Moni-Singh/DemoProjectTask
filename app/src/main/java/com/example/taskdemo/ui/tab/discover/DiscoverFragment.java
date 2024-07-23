package com.example.taskdemo.ui.tab.discover;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
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
    private List<Product> sliderProducts = new ArrayList<>();
    private View progressLayout;
    private int productId;
    private StickyHeaderAdapter stickyHeaderAdapter = new StickyHeaderAdapter(this, this);
    private List<Product> products = new ArrayList<>();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 10001;
    private static final int BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE = 10002;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng eventLocation = new LatLng(23.0384903236922, 72.51296554003244);  // Event location
    private float geofenceRadius = 3000; // Radius of the geofence

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
        if (isLocationEnabled()) {
            requestLocationUpdates(productId);
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
    }

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

    @Override
    public void onItemClick(int id) {
        this.productId = id;
        Log.d("clickedf", "clicked");
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
        } else if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            requestLocationUpdates(id);
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void requestLocationUpdates(int id) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000); // 5 seconds interval
        locationRequest.setFastestInterval(2000); // 2 seconds fastest interval

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult.getLastLocation() != null) {
                    Location location = locationResult.getLastLocation();
                    processLocation(location, id);
                    fusedLocationClient.removeLocationUpdates(this); // Stop location updates
                } else {
                    Toast.makeText(mContext, "Unable to get location. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if (!locationAvailability.isLocationAvailable()) {
                    Toast.makeText(mContext, "Location services not available.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void processLocation(Location location, int id) {
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
            showEventAlertDialog();
            Toast.makeText(mContext, "You are at the event location!", Toast.LENGTH_SHORT).show();
        } else {
            if (MainActivity.eventLocation.contains(id)) {
                MainActivity.eventLocation.remove(Integer.valueOf(id)); // Safely remove by value
            }
            refreshData();
            showDistanceAlertDialog(distance);
        }
    }

    private void showEventAlertDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("Event Location")
                .setMessage("Gateway Group. You are at location")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void showDistanceAlertDialog(float distance) {
        new AlertDialog.Builder(mContext)
                .setTitle("You are too far!")
                .setMessage("You are " + String.format("%.2f", distance) + " meters away from the event location.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, attempt to get location again
                requestLocationUpdates(productId);
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(mContext, "Location permission is required to check your location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
