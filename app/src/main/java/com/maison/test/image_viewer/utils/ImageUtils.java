package com.maison.test.image_viewer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Christophe on 03/06/2016.
 * * Permet d'éviter les problèmes mémoire au chargement des images
 * <p/>
 * https://developer.android.com/training/displaying-bitmaps/load-bitmap.html
 */
public class ImageUtils {
    private static ImageUtils ourInstance = new ImageUtils();

    public static ImageUtils getInstance() {
        return ourInstance;
    }

    private ImageUtils() {
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Chargement d'une image à partir d'une ressource
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                  int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * Chargement d'une image à partir d'une URL
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampledBitmapFromURL(String path,
                                             int reqWidth, int reqHeight) {
        try {
            URL url = new URL(path);

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(url.openConnection().getInputStream(),null,options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;

            return BitmapFactory.decodeStream(url.openConnection().getInputStream(),null,options);

        } catch (MalformedURLException ex) {
            Log.d("URLException : ", ex.getMessage());
        } catch (IOException ex) {
            Log.d("IOException : ", ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Chargement d'une image à partir du filesystem
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampledBitmapFromFile(String path,
                                              int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * Sauvegarde d'une image dans le répertoire privé de l'application
     * @param activity
     * @param bitmapImage
     * @param filename
     * @return
     */
    public String saveToInternalStorage(Activity activity, Bitmap bitmapImage, String filename) {
        ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
        String fileToSave = filename.substring(filename.lastIndexOf('/') + 1);
        File directory = cw.getDir("image_dir", Context.MODE_PRIVATE);
        File mypath = new File(directory, fileToSave);

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 85, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    /**
     * Lance une asynctask pour lancer le chargement d'une image
     * Optimize les performances des adapteurs
     * @param image
     * @return
     */
    public Bitmap getImageFromWS(String image) {
        Bitmap result = null;

        try {
            result = new ImageLoader().execute(image).get();
        } catch (Exception e) {
            Log.d("AsyncTask error : ", e.getMessage());
        }
        return result;
    }

    private class ImageLoader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            return decodeSampledBitmapFromURL(params[0], 350, 350);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
        }

    }
}
