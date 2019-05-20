package com.meizu.upspushdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.meizu.upspushsdklib.UpsPushManager;

public class UpsPermissionActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            boolean hasPermission = false;
            for (String permission : permissions) {
                if (permission.equalsIgnoreCase(Manifest.permission.READ_PHONE_STATE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        hasPermission = true;
                        break;
                    }
                }
            }
            if (hasPermission) {
                Toast.makeText(this, "权限开启成功", Toast.LENGTH_SHORT).show();
                UpsPushManager.register(this, MainActivity.UPS_APP_ID, MainActivity.UPS_APP_KEY);
            } else {
                Toast.makeText(this, "权限开启失败", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }
}
