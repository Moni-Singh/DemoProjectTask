package com.example.taskdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;
import java.io.OutputStream;

public class BitmapUtils {

    public static Uri saveImageToInternalStorage(Context context, Bitmap bitmap) {
        Uri imageUri = null;
        try {
            String imageName = "profile_image_" + System.currentTimeMillis() + ".png";
            OutputStream fos = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            imageUri = Uri.fromFile(context.getFileStreamPath(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUri;
    }
}
