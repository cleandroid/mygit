package com.example.android;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class StartActivity extends AppCompatActivity {

    public static final int RequestPermissionCode = 7;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkPermission()){
                Toast.makeText(this, "CheckPermission Ok", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(StartActivity.this,MainActivity.class));
                finish();
            } else {
                RequestMultiplePermission();
            }
        }else{

        }



    }

    public void RequestMultiplePermission() {
        ActivityCompat.requestPermissions(StartActivity.this, new String[]{
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE
                    }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean ReadPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WritePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (ReadPermission && WritePermission) {
                        Toast.makeText(this, "CheckPermission Ok", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StartActivity.this,MainActivity.class));
                        finish();
                    } else {
                        finish();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int ReadPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int WritePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return ReadPermissionResult == PackageManager.PERMISSION_GRANTED && WritePermissionResult == PackageManager.PERMISSION_GRANTED ;

    }

}
