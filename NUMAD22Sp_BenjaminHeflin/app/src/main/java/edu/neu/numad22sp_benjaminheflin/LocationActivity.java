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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class LocationActivity extends AppCompatActivity {

    private static final int PERMISSIONS_LOCATION = 1;
    TextView latVal, longVal;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latVal = findViewById(R.id.LatVal);
        longVal = findViewById(R.id.LongVal);

        latVal.setText("");
        longVal.setText("");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        updateGPS();
    }

    private void inputValues(Location location) {

        latVal.setText(String.valueOf(location.getLatitude()));
        longVal.setText(String.valueOf(location.getLongitude()));

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
                    Toast.makeText(this.getApplicationContext(), "Location Permission not granted.", Toast.LENGTH_SHORT).show();
                }
        }

    }

    private void updateGPS() {

        boolean coarsePermissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (coarsePermissionGranted) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    inputValues(location);
                }
            });
        }
        else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_LOCATION);
        }
    }
}