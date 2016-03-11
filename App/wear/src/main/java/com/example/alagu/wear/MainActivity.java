package com.example.alagu.wear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private String s ="not shaked";
    public static String place = null;
    private ArrayList<String> postalc = new ArrayList<String>();

    public String loadJSONFromAsset() {
        Log.d("T", "to get");
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("us_postal_codes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            //json = buffer.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        place = intent.getStringExtra("place");
        Log.d("T", "mode is: " + place);
        //String[] words = place.split(" ");

        if (place != null) {
            //final DotsPageIndicator mPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
            final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
            pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager(), place));
            //mPageIndicator.setPager(pager);
        }

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                Log.d("T", "shaked");
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                //mode="currentlocation";
                //pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager(), mode));
                handleShakeEvent();
            }
        });




    }



        public void handleShakeEvent() {
            Log.d("T", "in handleshakeevent");
            try {
                Log.d("T", "iin try");
                JSONArray objarray = new JSONArray(loadJSONFromAsset());
                for (int k = 0; k < objarray.length(); k++) {
                    JSONObject obje = objarray.getJSONObject(k);
                    //Log.d("T", "samp are " + obje.getString("county-name"));
                    postalc.add(obje.getString("Postal Code"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
           // Random ran = new Random();
            //int x = 10001 + (int)(Math.random() * 99999);
           //int x = 10001 + (int)(Math.random() * ((99999 - 10001) + 1));
            //int x = ran.nextInt(10001) + 99999;

            //int x = 94703;
            Random randomno = new Random();
            int x = randomno.nextInt(postalc.size());
            //int x = 93240;
            String xst = postalc.get(x);
            //String xst =Integer.toString(x);
            Log.d("T", "number is " + xst);

        //final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        //pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager(), place));
            Intent sendIntent = new Intent(this, WatchtoPhoneService.class);
            sendIntent.putExtra("shakemode", xst);
            startService(sendIntent);

    }



        /*if (s.equals("shaked")){
            mode="currentlocation";
            final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
            pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager(), mode));
        }*/




    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
