package com.example.laboratory8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    GoogleMap map;
    Button btnGetDirection;
    MarkerOptions place1, place2;
    Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        place1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location 1");
        place1 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location 2");

        String url = getUrl(place1.getPosition(), place2.getPosition(), "driving");

        new FetchURL(MainActivity.this).execute(url, "driving");
    }


    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        //  Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_desk = "destination" + dest.latitude + "." + dest.longitude;
        // Mode
        String mode = "mode" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_desk + "&" + mode;
        // Output format
        String output = "json";
        // Building the irl to the web service
        String url = "https://maps.googleapis.com/maps/api/direction/" + output + "?" + parameters + "&key=AIzaSyAQ-0IqNPjP5_WRAIEF7OxnQ31RRKchars";
        Log.d("ERROR", url);

        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }
}