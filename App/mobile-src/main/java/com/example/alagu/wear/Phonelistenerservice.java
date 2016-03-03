package com.example.alagu.wear;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class Phonelistenerservice extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String john = "/John White";
    private static final String bill = "/Bill West";
    private static final String amber = "/Amber Law";
    private static final String loc = "/currentLocation";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase(john) ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, Detail.class );

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(CongView.EXTRA_MESSAGE, "j");
            Log.d("T", "about to start watch MainActivity with john");
            startActivity(intent);

            // Make a toast with the String

            // so you may notice this crashes the phone because it's
            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
            // replace sending a toast with, like, starting a new activity or something.
            // who said skeleton code is untouchable? #breakCSconceptions

        } else if( messageEvent.getPath().equalsIgnoreCase(bill) ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, Detail.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(CongView.EXTRA_MESSAGE, "h");
            Log.d("T", "about to start watch MainActivity with bill");
            startActivity(intent);
        } else if( messageEvent.getPath().equalsIgnoreCase(amber) ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, Detail.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(CongView.EXTRA_MESSAGE, "d");
            Log.d("T", "about to start watch MainActivity with amber");
            startActivity(intent);
        } else if( messageEvent.getPath().equalsIgnoreCase(loc) ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, CongView.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(CongView.EXTRA_MESSAGE, "l");
            Log.d("T", "about to start watch MainActivity with amber");
            startActivity(intent);
        }else {
            super.onMessageReceived( messageEvent );
        }

    }
}

