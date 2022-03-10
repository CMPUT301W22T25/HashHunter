package com.example.hashhunter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Include method to display image from url:
 * - https://www.geeksforgeeks.org/how-to-load-any-image-from-url-without-using-any-dependency-in-android/
 * - https://stackoverflow.com/questions/11831188/how-to-get-bitmap-from-a-url-in-android
 */
public class Photo {
    private String username; // owner of the photo
    private String urlString;
    private Bitmap imageBitmap;

    public Photo(String urlString) {
        this.urlString = urlString; // cache url
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

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
}
