package com.example.testmap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e("TAG", "GeofencingEvent error: " + geofencingEvent.getErrorCode());
            return;
        }
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            // Handle geofence enter event
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // Handle geofence exit event
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
            // Handle geofence dwell event
        }
    }
}