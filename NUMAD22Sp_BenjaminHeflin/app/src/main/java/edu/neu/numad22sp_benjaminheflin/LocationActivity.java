package edu.neu.numad22sp_benjaminheflin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class LocationActivity extends AppCompatActivity {

    private static final int PERMISSIONS_LOCATION = 1;
    TextView latVal, longVal, locTypeVal;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latVal = findViewById(R.id.LatVal);
        longVal = findViewById(R.id.LongVal);
        locTypeVal = findViewById(R.id.LocTypeVal);

        latVal.setText("");
        longVal.setText("");
        locTypeVal.setText("");

        locationRequest = new LocationRequest();
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        updateGPS();
    }

    private void inputValues(Location location, String locationType) {

        latVal.setText(String.valueOf(location.getLatitude()));
        longVal.setText(String.valueOf(location.getLongitude()));
        locTypeVal.setText(locationType);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode) {
            case PERMISSIONS_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                }
                else {
                    Toast.makeText(this.getApplicationContext(), "No permission granted.", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }

    }

    private void updateGPS() {

        boolean finePermissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean coarsePermissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (finePermissionGranted) {
            Toast.makeText(this.getApplicationContext(), "Fine Permission Granted", Toast.LENGTH_SHORT).show();
        }
        if (coarsePermissionGranted) {
            Toast.makeText(this.getApplicationContext(), "Coarse Permission Granted", Toast.LENGTH_SHORT).show();
        }

        if (finePermissionGranted) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    inputValues(location, "FINE");
                }
            });
        }

        else if (coarsePermissionGranted) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    inputValues(location, "COARSE");
                }
            });
        }

        else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_LOCATION);
        }
    }
}