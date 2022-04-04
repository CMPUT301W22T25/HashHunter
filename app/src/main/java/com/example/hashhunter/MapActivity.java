package com.example.hashhunter;

import static com.example.hashhunter.MainActivity.PREF_USERNAME;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.libraries.maps.model.Marker;
import com.google.android.libraries.maps.model.MarkerOptions;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
/**
 * Map activity showcasing geolocation of player and QR codes
 * References: GPS location app: https://www.youtube.com/watch?v=l-J6gDYtgFU Google Maps fragment: https://www.youtube.com/watch?v=p0PoKEPI65o
 *
 */
public class MapActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private SupportMapFragment supportMapFragment;
    private SharedPreferences sharedPreferences;
    private String username;
    // Main function that asks for permissions, initializes google map fragment, and adds markers on fragments and clicking on markers to go to qr visualizer activity
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

        //Get shared preferences info for username
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_USERNAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(PREF_USERNAME, null);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            /** When location values are received from phone, show map and plot points on map
             * @Param location
             *          location values from phone
             */
            public void onLocationChanged(@NonNull Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    /**
                     * Once map is ready get the latitude and longitude of location values and place player marker
                     * @param googleMap
                     *          google map fragment
                     */
                    public void onMapReady(GoogleMap googleMap) {
                        // Get Location of player and put them on the map as a marker
                        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions options = new MarkerOptions().position(latlng).title("Player");
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16));
                        googleMap.addMarker(options);
                        // Add all QR codes with location data to the map
                        /**
                         * Once firebase data of all QR code objects that contain latitude and longitudes is received, place them as markers on the map
                         *
                         */
                        FirestoreController.getGameCodeListWithLocation().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                            for(QueryDocumentSnapshot document : task.getResult()) {
                                                GameCode QRCode = document.toObject(GameCode.class);
                                                LatLng codeLatLng = new LatLng(QRCode.getLatitude(),QRCode.getLongitude());
                                                MarkerOptions options = new MarkerOptions().position(codeLatLng).title(document.get("title").toString());
                                                googleMap.addMarker(options);
                                            }
                                        } else {
                                            Log.d("Error occurred", String.valueOf(task.getException()));
                                        }
                            }
                        });
                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            /**
                             * On clicking marker on map, move activities to QR visualizer activity
                             * @param marker
                             *        google map marker
                             * @return boolean
                             */
                            //Adds on click method for all markers that are not the player marker
                            public boolean onMarkerClick(Marker marker) {
                                String markerTitle = marker.getTitle();
                                if (markerTitle != "Player") {
                                    FirestoreController.getGameCodeListWithTitle(markerTitle).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    //Get gamecode at marker and then create gamecode controller from gamecode and send to visualizer activity
                                                    GameCode QRCode = document.toObject(GameCode.class);
                                                    GameCodeController gameCodeController = new GameCodeController(QRCode);
                                                    Intent intent = new Intent(MapActivity.this, QRVisualizerActivity.class);
                                                    gameCodeController.setDataBasePointer(document.getId());
                                                    intent.putExtra("QR ITEM", gameCodeController);
                                                    intent.putExtra("USERNAME", username);
                                                    startActivity(intent);
                                                }
                                            } else {
                                                Log.d("Error occurred", String.valueOf(task.getException()));
                                            }
                                        }
                                    });
                                }
                                return false;
                            }
                        });
                    }
                });
            }
        });
    }
}