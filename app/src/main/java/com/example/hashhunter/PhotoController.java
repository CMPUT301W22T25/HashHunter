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
 * Controller class to display image from url:
 * - https://www.geeksforgeeks.org/how-to-load-any-image-from-url-without-using-any-dependency-in-android/
 * - https://stackoverflow.com/questions/11831188/how-to-get-bitmap-from-a-url-in-android
 */
public class PhotoController {
    private Photo photo;
    private Bitmap imageBitmap;

    /**
     * empty constructor, to be used for firebase
     */
    public PhotoController(){
    }

    /**
     * constructor that uses a Photo object
     * @param myPhoto a Photo object
     */
    public PhotoController(Photo myPhoto){
        this.photo = myPhoto;
    }

    /**
     * constructor that uses a string url of the photo
     * @param urlString url of the photo
     */
    public PhotoController(String urlString) {
        photo = new Photo(urlString);
    }

    /**
     * constructor that uses a string url of the photo and a string id of the owner
     * @param urlString url of the photo
     * @param owner id of the woner
     */
    public PhotoController(String urlString, String owner) {
        photo = new Photo(urlString, owner);
    }

    /**
     * converts the url of the photo to a bitmap
     */
    private void URLToBitmap() {
        // convert url to bitmap
        try {
            URL url = new URL(photo.getUrl());
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageBitmap = image;
        } catch(IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * displays the image
     * @param imageView the ImageView on which the image is to be displayed
     */
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
