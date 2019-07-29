package com.example.directions;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocompleteFragment;
import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;
public class DirectionActivity extends AppCompatActivity {
    ImageButton car;
    ImageButton train;
    ImageButton walk;
    String direction_mod="driving";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        car=findViewById(R.id.car);
        train=findViewById(R.id.train);
        walk=findViewById(R.id.walk);
        setupAutoCompleteFragment();
    }
    public void btn_click(View v){
        car.setImageResource(R.drawable.car);
        train.setImageResource(R.drawable.train);
        walk.setImageResource(R.drawable.walk);
        ImageButton choose=findViewById(v.getId());
        if(v.getId()==R.id.car){
            choose.setImageResource(R.drawable.car2);
            direction_mod="driving";
        }
        else if(v.getId()==R.id.train){
            choose.setImageResource(R.drawable.train2);
            direction_mod="transit";
        }
        else if(v.getId()==R.id.walk){
            choose.setImageResource(R.drawable.walk2);
            direction_mod="walking";
        }
    }
    private void setupAutoCompleteFragment(){
        final Intent returnIntent = new Intent();
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);
        autocompleteFragment.setHint("Your location");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng latLng= place.getLatLng();
                returnIntent.putExtra("lat",latLng.latitude);
                returnIntent.putExtra("lon",latLng.longitude);
            }
            @Override
            public void onError(Status status) {
                Toast.makeText(DirectionActivity.this, "Failed search", Toast.LENGTH_SHORT).show();
            }
        });
        PlaceAutocompleteFragment autocompleteFragment2 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng latLng= place.getLatLng();
                returnIntent.putExtra("DirectionMod",direction_mod);
                returnIntent.putExtra("lat2",latLng.latitude);
                returnIntent.putExtra("lon2",latLng.longitude);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
            @Override
            public void onError(Status status) {
                Toast.makeText(DirectionActivity.this, "Failed search", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
