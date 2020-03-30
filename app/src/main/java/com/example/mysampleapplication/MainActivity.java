package com.example.mysampleapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static androidx.core.content.FileProvider.getUriForFile;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
    }

    public void onSaveClicked(View view){
        String result = null;
        try {
          result = (String) new SaveImageToInternalStorage().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(result != null){
            Log.i(TAG, "image saved in : " + result);
            /*File imagePath = new File("/data/user/0/com.example.mysampleapplication/app_imageDir");
            File newFile = new File(imagePath, "myTestImage.jpg");
            Uri contentUri = getUriForFile(mContext, "com.example.mysampleapplication.provider", newFile);
            Log.i(TAG, "contentUri: " + contentUri);*/
        }
    }

    public class SaveImageToInternalStorage extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            ContextWrapper contextWrapper = new ContextWrapper(mContext);
            File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
            File mypath=new File(directory,"myTestImage.jpg");

            Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.test_image);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
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
    }
}
