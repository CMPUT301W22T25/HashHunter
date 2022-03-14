//package com.example.hashhunter;
//
//import static android.content.Context.LOCATION_SERVICE;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.os.Looper;
//import android.provider.Settings;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.libraries.maps.SupportMapFragment;
//
//public class MapFragment extends Fragment {
//    //https://www.youtube.com/watch?v=l-J6gDYtgFU
//    //Get GPS location
//    //https://www.youtube.com/watch?v=p0PoKEPI65o
//    // Output google maps fragment
//    private ActivityResultLauncher<String[]> activityResultLauncher;
//    private LocationManager locationManager;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private SupportMapFragment supportMapFragment;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
//
//        if (ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//            getCurrentLocation();
//
//
//
//        } else {
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
//
//        }
//        return inflater.inflate(R.layout.activity_map,container,false);
//
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 100 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
//            getCurrentLocation();
//        } else {
//            Toast.makeText(getActivity(),"Permission denied",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private void getCurrentLocation() {
//        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
//        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                @Override
//                public void onComplete(@NonNull Task<Location> task) {
//                    Location location = task.getResult();
//                    if (location != null) {
////                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
////                            @Override
////                            public void onMapReady(GoogleMap googleMap) {
////                                MarkerOptions markerOptions = new MarkerOptions();
////                                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
////                                markerOptions.position(latLng);
////                                markerOptions.title("Player");
////                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
////                                googleMap.addMarker(markerOptions);
////                            }
////                        });
//                    } else {
//                        LocationRequest locationRequest = new LocationRequest()
//                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                                .setInterval(10000)
//                                .setFastestInterval(1000)
//                                .setNumUpdates(1);
//                        LocationCallback locationCallback = new LocationCallback() {
//                            @Override
//                            public void onLocationResult(LocationResult locationResult){
//                                Location location1 = locationResult.getLastLocation();
//                            }
//
//                        };
//                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
//                                locationCallback, Looper.myLooper());
//
//                    }
//
//                }
//            });
//        } else {
//            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        }
//
//    }
//}