package com.example.alagu.wear;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.wearable.Wearable;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "aVIZ05TLsrZWkXBHN6ME6mylM";
    private static final String TWITTER_SECRET = "7XqpKUIszwPu91OFiFzSfyvXYIsveSpzNzjsPcMQI3sP8LOgI7";
    private TwitterLoginButton loginButton;

    //there's not much interesting happening. when the buttons are pressed, they start
    //the PhoneToWatchService with the cat name passed in.

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private double currentLongitude;
    private double currentLatitude;
    private String zip;
    private String state;
    private String county;
    private GoogleMap mMap;
    private Button zipButton;
    private Button locButton;
    //private EditText place;
    private String p;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        //super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)  // used for data layer API
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                loginButton.setVisibility(View.GONE);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });


        zipButton = (Button) findViewById(R.id.go1);
        locButton = (Button) findViewById(R.id.go2);



        zipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText place = (EditText) findViewById(R.id.ziptext);
                String pl = place.getText().toString();
                Log.d("T", "the entered is" + pl);

                Intent viewintent = new Intent(getBaseContext(), getvalues.class);
                //viewintent.putExtra(CongView.EXTRA_MESSAGE, "z");
                viewintent.putExtra("place", pl);
                startActivity(viewintent);
                //Log.d("T", p);
                //Log.d("T",zip);
            }
        });

        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Geocoder myLocation = new Geocoder(getApplicationContext(), Locale.getDefault());
                try
                {
                    List<Address> myList = myLocation.getFromLocation(currentLatitude, currentLongitude, 1);
                    zip = myList.get(0).getPostalCode();
                    //state = myList.get(0).getAdminArea();
                    //Log.d("T", state);
                    //county = myList.get(0).getPostalCode();
                    Log.d("T", "zipcode from geo" + zip);
                }
                catch(IOException e)
                {
                    Log.d("T", "No zipcode");
                }

                Intent viewintent = new Intent(getBaseContext(), getvalues.class);
                //viewintent.putExtra(CongView.EXTRA_MESSAGE, "z");
                viewintent.putExtra("place", zip);
                startActivity(viewintent);

                Log.d("T", zip);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(Boolean.parseBoolean("True"));
        //Location loc = mMap.getMyLocation();
        //currentLongitude = loc.getLongitude();
        //currentLatitude =loc.getLatitude();

        /*LocationManager locationManager = (LocationManager)getSystemService
                (Context.LOCATION_SERVICE);
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            //Criteria criteria = new Criteria();
            //String bestProvider = locationManager.getBestProvider(criteria, true);
            //Location getLastLocation = locationManager.getLastKnownLocation(bestProvider);
            Location getLastLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        currentLongitude = getLastLocation.getLongitude();
        currentLatitude = getLastLocation.getLatitude();}*/
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        /*Geocoder myLocation = new Geocoder(getApplicationContext(), Locale.getDefault());
        try
        {
            List<Address> myList = myLocation.getFromLocation(currentLatitude, currentLongitude, 1);
            zip = myList.get(0).getPostalCode();
        }
        catch(IOException e)
        {
            Log.d("T", "No zipcode");
        }*/
    }


    /*private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }*/



    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //mLatitudeText = (String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText = (String.valueOf(mLastLocation.getLongitude()));
            currentLatitude = mLastLocation.getLatitude();
            currentLongitude = mLastLocation.getLongitude();
            Log.d("T", (String.valueOf(mLastLocation.getLatitude())));
            Log.d("T", (String.valueOf(mLastLocation.getLongitude())));
        }


    }

    /*@Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }
    /*@Override
    public void onConnected(Bundle bundle) {}*/

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connResult) {}








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
