package com.example.alagu.wear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private String s ="not shaked";
    private String mode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        Log.d("T", "mode is: " + mode);

        //final DotsPageIndicator mPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager(), mode));
        //mPageIndicator.setPager(pager);

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
        });}

        public void handleShakeEvent() {
            Log.d("T", "in handleshakeevent");
        mode="currentLocation";
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager(), mode));
            Intent sendIntent = new Intent(this, WatchtoPhoneService.class);
            sendIntent.putExtra("mode", "currentLocation");
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
