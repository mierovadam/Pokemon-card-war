package com.example.cardgame.Records;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class Record {
    private String name;
    private String pokemon;
    private int score;
    private double longitude;
    private double latitude;

    public Record(String name,String pokemon, int score, double longitude, double latitude){
        this.score = score;
        this.pokemon = pokemon;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Record(){}

    public String getPokemon() {
        return pokemon;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }




    @Override
    public String toString() {
        return "Record{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
