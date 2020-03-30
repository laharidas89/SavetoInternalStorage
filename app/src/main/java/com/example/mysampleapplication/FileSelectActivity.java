/*
package com.example.mysampleapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;

public class FileSelectActivity extends Activity {
    // The path to the root of this app's internal storage
    private File privateRootDir;
    // The path to the "images" subdirectory
    private File imagesDir;
    // Array of files in the images subdirectory
    File[] imageFiles;
    // Array of filenames corresponding to imageFiles
    String[] imageFilenames;
    // Initialize the Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set up an Intent to send back to apps that request a file
        Intent resultIntent =
                new Intent("com.example.mysampleapplication.ACTION_RETURN_FILE");
        // Get the files/ subdirectory of internal storage
        privateRootDir = getFilesDir();
        // Get the files/images subdirectory;
        //imagesDir = new File(privateRootDir, "images");
        // Get the files in the images subdirectory
        imageFiles = privateRootDir.listFiles();
        // Set the Activity's result to null to begin with
        setResult(Activity.RESULT_CANCELED, null);
        */
/*
         * Display the file names in the ListView fileListView.
         * Back the ListView with the array imageFilenames, which
         * you can create by iterating through imageFiles and
         * calling File.getAbsolutePath() for each File
         *//*

    }
}
*/
