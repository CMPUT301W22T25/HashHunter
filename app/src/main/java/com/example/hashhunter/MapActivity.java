package com.example.hashhunter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.maps.CameraUpdateFactory;
import com.google.android.libraries.maps.GoogleMap;
import com.google.android.libraries.maps.OnMapReadyCallback;
import com.google.android.libraries.maps.SupportMapFragment;
import com.google.android.libraries.maps.model.LatLng;
import com.google.android.libraries.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MapActivity extends AppCompatActivity {
    /**
     * Map activity showcasing geolocation of player and QR codes
     * @References GPS location app: https://www.youtube.com/watch?v=l-J6gDYtgFU Google Maps fragment: https://www.youtube.com/watch?v=p0PoKEPI65o
     *
     */

    private LocationManager locationManager;
    private SupportMapFragment supportMapFragment;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        // If permissions are not granted, ask for location permissions
        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED&&
        ContextCompat.checkSelfPermission(MapActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,},1);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            /** When location values are received from phone, show map and plot points on map
             * @Param Location
             */
            public void onLocationChanged(@NonNull Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    /**
                     * Once map is ready get the latitude and longitude of location values and place player marker
                     * @param GoogleMap
                     */
                    public void onMapReady(GoogleMap googleMap) {
                        // Get Location of player and put them on the map as a marker
                        LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
                        MarkerOptions options = new MarkerOptions().position(latlng).title("Player");
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));
                        googleMap.addMarker(options);
                        // Add all QR codes with location data to the map
                        db.collection("GameCode")
                                .whereNotEqualTo("latitude",null)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    /**
                                     * Once firebase data of all QR code objects that contain latitude and longitudes is received, place them as markers on the map
                                     */
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            for(QueryDocumentSnapshot document : task.getResult()) {
                                                LatLng codeLatLng = new LatLng((Double)document.get("latitude"),(Double)document.get("longitude"));
                                                MarkerOptions options = new MarkerOptions().position(codeLatLng).title(document.get("title").toString());
                                                googleMap.addMarker(options);
                                            }
                                        } else {
                                            Log.d("Error occurred", String.valueOf(task.getException()));
                                        }
                                    }
                                });

                    }
                });
            }
        });

    }
}