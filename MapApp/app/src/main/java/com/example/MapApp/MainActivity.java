package com.example.MapApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.groupf22_hw06.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
    String API_KEY = "AIzaSyBSKI9t4mWubU7vdqibuFACFAy2KuGeW5o";

    Button clear, directions, gasStations, restaurants;
    LatLng startPlace, endPlace;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), API_KEY);
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mapContainer, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);



        //    If you are using the Autocomplete fragment, and need to override onActivityResult
//    you must call super.onActivityResult, otherwise the fragment will not function properly.



        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                startPlace = place.getLatLng();
                Log.i("demo", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("demo", "An error occurred: " + status);
            }
        });

        AutocompleteSupportFragment autocompleteFragmentStop = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment2);

        // Specify the types of place data to return.
        autocompleteFragmentStop.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragmentStop.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                endPlace = place.getLatLng();
                Log.i("demo", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("demo", "An error occurred: " + status);
            }
        });
        
        
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions()
                .position(startPlace)
                .title("Start"));
        googleMap.addMarker(new MarkerOptions()
                .position(endPlace)
                .title("Start"));
    }
}