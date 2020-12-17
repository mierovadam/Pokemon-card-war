package com.example.cardgame.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cardgame.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Fragment_Map extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private LatLng[] latLngs;
    private CallBack_MapLocation callBack_mapLocation;

    public void setCallBack_mapLocation(CallBack_MapLocation callBack_mapLocation){
        this.callBack_mapLocation = callBack_mapLocation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);

        if(callBack_mapLocation != null){  //get latLngs info from topTenMap activity
            latLngs = callBack_mapLocation.getLatLngData();
        }
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.top_frag_map);
        if(supportMapFragment != null)
            supportMapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        for(int i=0 ; i<latLngs.length ; i++){
            googleMap.addMarker(new MarkerOptions().position(latLngs[i]));
        }
    }

    public void focusRecord(int pos) {
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latLngs[pos], 15);
        googleMap.animateCamera(location);
    }
}
