package com.example.mysampleapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import static androidx.core.content.FileProvider.getUriForFile;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String AUTHORITY = "com.example.mysampleapplication.fileprovider";
    private Context mContext;
    private Uri mContentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
    }

    public void onSaveClicked(View view) {
        try {
            new SaveImageToInternalStorage().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class SaveImageToInternalStorage extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            File myImagePath = new File(mContext.getFilesDir(), "myTestImage.jpg");

            Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.test_image);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(myImagePath);
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
            return myImagePath.getAbsolutePath();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String result = (String) o;
            Log.i(TAG, "image saved at : " + result);
            File newFile = new File(result);

            try {
                if (mContext.getPackageManager().resolveContentProvider(AUTHORITY, PackageManager.GET_META_DATA) != null) {
                    mContentUri = FileProvider.getUriForFile(mContext, AUTHORITY, newFile);
                    Log.i(TAG, "contentUri: " + mContentUri);

                    Intent resultIntent = new Intent(Intent.ACTION_SEND);
                    if (mContentUri != null) {
                        resultIntent.addFlags(
                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        resultIntent.setDataAndType(
                                mContentUri,
                                getContentResolver().getType(mContentUri));
                        MainActivity.this.setResult(Activity.RESULT_OK,
                                resultIntent);
                    } else {
                        resultIntent.setDataAndType(null, "");
                        MainActivity.this.setResult(RESULT_CANCELED,
                                resultIntent);
                    }
                    startActivityForResult(resultIntent, 0);
                }
            } catch (Exception e) {
                Log.e(TAG, "getUriForFile failed: " + e.toString());
            }
        }
    }
}
