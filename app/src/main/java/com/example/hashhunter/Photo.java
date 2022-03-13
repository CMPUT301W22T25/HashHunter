package com.example.hashhunter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Include method to display image from url:
 * - https://www.geeksforgeeks.org/how-to-load-any-image-from-url-without-using-any-dependency-in-android/
 * - https://stackoverflow.com/questions/11831188/how-to-get-bitmap-from-a-url-in-android
 */
public class Photo {
    private String owner; // owner of the photo
    private String urlString;
    private Bitmap imageBitmap;

    public Photo(String urlString) {
        this.urlString = urlString; // cache url
    }
    public Photo (String urlString, String owner) {
        this.urlString = urlString;
        this.owner = owner;
    }

    private void URLToBitmap() {
        // convert url to bitmap
        try {
            URL url = new URL(urlString);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageBitmap = image;
        } catch(IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayImage(ImageView imageView) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            //Background work here
            URLToBitmap();
            handler.post(() -> {
                //UI Thread work here
                imageView.setImageBitmap(imageBitmap);
            });
        });
    }
}
