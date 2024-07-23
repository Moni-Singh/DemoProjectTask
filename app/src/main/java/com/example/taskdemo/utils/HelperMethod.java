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
                .setTitle(R.string.logout)
                .setMessage(R.string.are_you_sure_want_to_logout)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    if (onConfirmAction != null) {
                        onConfirmAction.run();
                    }
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }


    public static void showAlertDialog(Context mContext,String title, String message) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

}