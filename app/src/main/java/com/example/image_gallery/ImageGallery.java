package com.example.image_gallery;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

public class ImageGallery {

    public static ArrayList<Uri> getAllImagesFromGallery(Context context) {
        ArrayList<Uri> imageUris = new ArrayList<>();
        Uri imagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.Images.Media._ID};

        Cursor cursor = context.getContentResolver().query(
                imagesUri,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED + " DESC"
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                long id = cursor.getLong(idColumn);
                Uri uri = ContentUris.withAppendedId(imagesUri, id);
                imageUris.add(uri);
            }
            cursor.close();
        }

        return imageUris;
    }

}
