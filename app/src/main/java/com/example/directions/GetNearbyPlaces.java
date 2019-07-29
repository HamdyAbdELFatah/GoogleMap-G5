package com.example.directions;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaces extends AsyncTask<Object, String, String>
{
    private String googleplaceData, url;
    private GoogleMap mMap;
    private String markFlag="";
    @Override
    protected String doInBackground(Object... objects)
    {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
       markFlag=(String) objects[2];
        DownLoadUrl downloadUrl = new DownLoadUrl();
        try
        {
            googleplaceData = downloadUrl.ReadTheUrl(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return googleplaceData;
    }
    @Override
    protected void onPostExecute(String s)
    {
        List<HashMap<String, String>> nearByPlacesList = null;
        DataParser dataParser = new DataParser();
        nearByPlacesList = dataParser.parse(s);
        DisplayNearbyPlaces(nearByPlacesList);
    }
    private void DisplayNearbyPlaces(List<HashMap<String, String>> nearByPlacesList)
    {
        for (int i=0; i<nearByPlacesList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googleNearbyPlace = nearByPlacesList.get(i);
            String nameOfPlace = googleNearbyPlace.get("place_name");
            String vicinity = googleNearbyPlace.get("vicinity");
            double lat = Double.parseDouble(googleNearbyPlace.get("lat"));
            double lng = Double.parseDouble(googleNearbyPlace.get("lng"));
            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(nameOfPlace + " : " + vicinity);
           if(markFlag.equals("restaurant"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.food));
            else if(markFlag.equals("cafe"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.coffee));
            else if(markFlag.equals("bar"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bar));
            else if(markFlag.equals("gym"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.gym));
            else if(markFlag.equals("art_gallery"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.art));
            else if(markFlag.equals("night_club"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.club));
            else if(markFlag.equals("library"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.books));
            else if(markFlag.equals("movie_theater"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.film));
            else if(markFlag.equals("museum"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.museum));
            else if(markFlag.equals("supermarket"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.groceries));
            else if(markFlag.equals("shopping_mall"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mall));
            else if(markFlag.equals("school"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.school));
            else if(markFlag.equals("hospital"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.kit));
            else if(markFlag.equals("gas_station"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.gas));
            else if(markFlag.equals("atm"))
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.atm));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        }
    }
}