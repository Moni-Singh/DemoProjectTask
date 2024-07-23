//package com.example.taskdemo.utils;
//
//import android.Manifest;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.LocationManager;
//
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//
//import com.example.taskdemo.ui.tab.discover.DiscoverFragment;
//
//import java.lang.ref.WeakReference;
//
//public class LocationSwitchStateReceiver extends BroadcastReceiver {
//    private WeakReference<Fragment> fragmentRef;
//
//    public LocationSwitchStateReceiver(Fragment fragment) {
//        this.fragmentRef = new WeakReference<>(fragment);
//    }
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Fragment fragment = fragmentRef.get();
//        if (fragment != null && intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION)) {
//            if (fragment instanceof DiscoverFragment) { // Replace YourFragment with your fragment class
//                DiscoverFragment discoverFragment = (DiscoverFragment) fragment;
//                if (DiscoverFragment.mMap != null) {
//                    if (DiscoverFragment.isLocationEnabled()) {
//                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                            DiscoverFragment.mMap.setMyLocationEnabled(true);
//                        }
//                    } else {
//                        DiscoverFragment.mMap.setMyLocationEnabled(false);
//                    }
//                }
//            }
//        }
//    }
//}
//
