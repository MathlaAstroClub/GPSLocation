package org.mathla.gpstes1;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int RC_LOCATION = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnShowLocation;
    private TextView tvLocation;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowLocation = (Button) findViewById(R.id.btnShowGPS);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        // init fusedLocationClient object
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // declare runtime permission for android M
                String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

                if (EasyPermissions.hasPermissions(MainActivity.this, perms)) {
                    // jika punya permission
                    getLastLocation();
                } else {
                    // tidak punya permission location
                    EasyPermissions.requestPermissions(MainActivity.this, "We need location permission",
                            RC_LOCATION, perms);
                }

            }
        });
    }

    private void getLastLocation() {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d(TAG, "onSuccess: " + location.getLatitude());

                        tvLocation.append(" " + location.getLatitude() + ", " + location.getLongitude());
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == RC_LOCATION) {
            getLastLocation();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == RC_LOCATION) {
            Toast.makeText(this, "Permission location denied", Toast.LENGTH_SHORT).show();
        }
    }
}
