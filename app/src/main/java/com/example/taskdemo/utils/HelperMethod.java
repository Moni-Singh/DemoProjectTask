package com.example.taskdemo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.taskdemo.R;


public class HelperMethod {


    //Check network is available or not
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }


    //Toast Message with Title
    public static void showToast(String title, Context context) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
    }

    //Toast for Error Message
    public static void showErrorToast(Context context) {
        Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
    }

    //Toast for Network Internet Connection
    public static void showGeneralNICToast(Context context) {
        Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
    }

    public static void showLogoutDialog(Context context, Runnable onConfirmAction) {
        new AlertDialog.Builder(context)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Execute the provided action
                    if (onConfirmAction != null) {
                        onConfirmAction.run();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Dismiss the dialog
                    dialog.dismiss();
                })
                .create()
                .show();
    }
}