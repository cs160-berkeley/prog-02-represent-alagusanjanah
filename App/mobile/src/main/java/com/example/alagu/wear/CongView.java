package com.example.alagu.wear;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.services.StatusesService;
import io.fabric.sdk.android.Fabric;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;




public class CongView extends Activity {
    private ArrayList<JSONArray> candidates;
    private ArrayList<JSONArray> listcounty;
    private String county;
    private String state;
    private JSONObject person1;
    private JSONObject person2;
    private JSONObject person3;
    private JSONObject person4;
    private String firstname1;
    private String lasttname1;
    private String partyname1;
    private String email1;
    private String website1;
    private String title1;
    private String dist;
    private String twitterid1;
    private String firstname2;
    private String lasttname2;
    private String partyname2;
    private String email2;
    private String website2;
    private String title2;
    private String twitterid2;
    private String firstname3;
    private String lasttname3;
    private String partyname3;
    private String email3;
    private String website3;
    private String title3;
    private String twitterid3;
    private String firstname4;
    private String lasttname4;
    private String partyname4;
    private String email4;
    private String website4;
    private String title4;
    private String twitterid4;
    private String obama;
    private String romney;
    private String tweet1;
    private String im1;
    private String tweet2;
    private String im2;
    private String tweet3;
    private String im3;
    private String tweet4;
    private String im4;


    private int n;
    private String mess;

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("election-county-2012.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    // Lookup the list of representatives.
    private class URLTask extends AsyncTask<String, Void, ArrayList<JSONArray>> {
        private JSONObject obj;
        public int count;
        private ArrayList<JSONArray> results = new ArrayList<JSONArray>(2);
        public String result;


        protected ArrayList<JSONArray> doInBackground(String... url) {

            try {
                InputStream is = new URL(url[0]).openStream();
                result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    Log.d("T", "Exception reading url" + e.toString());
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (Exception squish) {
                    }
                }
                JSONObject obj = new JSONObject(result.toString());
                JSONArray r = obj.getJSONArray("results");
                Log.d("T", "setting");
                results.add(r);
                Log.d("T", "set");
                is.close();
            } catch (Exception e) {
                Log.d("T", "Exception getting representatives list." + e.toString());
            }
            return results;
        }

        public int getcountofcandidates() {
            try {
                JSONObject obje = new JSONObject(result.toString());
                count = obje.getInt("count");
            } catch (Exception e) {

            }
            return count;


        }

    }

    private static final String TWITTER_KEY = "aVIZ05TLsrZWkXBHN6ME6mylM";
    private static final String TWITTER_SECRET = "7XqpKUIszwPu91OFiFzSfyvXYIsveSpzNzjsPcMQI3sP8LOgI7";
    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong_view);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        Intent viewintent = getIntent();
        Bundle extras = viewintent.getExtras();
        mess = extras.getString("place");
        tweet1 =extras.getString("t1");
        im1 =extras.getString("i1");
        tweet2 =extras.getString("t2");
        im2 =extras.getString("i2");
        tweet3 =extras.getString("t3");
        im3 =extras.getString("i3");

        //findobject();
        String u = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=" + mess + "&apikey=a15f9cd6a6dd42eda09c80085cf4c69e";

        URLTask urltask = new URLTask();
        urltask.execute(u);
        try {
            candidates = urltask.get();
        } catch (Exception e) {
        }

        try {
            n = urltask.getcountofcandidates();

        } catch (Exception e) {
        }

        if (n==4) {
            tweet4 =extras.getString("t4");
            im4 =extras.getString("i4");
        }


        String z = "https://maps.googleapis.com/maps/api/geocode/json?address="+mess+"&key=AIzaSyC7l_OeohAxB9XShaDjBpJa2pkKq0O3Geg";
        URLTask urltask6 = new URLTask();
        urltask6.execute(z);
        try {
            Log.d("T", "trying");
            listcounty = urltask6.get();
            Log.d("T", "len is "+Integer.toString(listcounty.size()));
            JSONArray arr = listcounty.get(0);
            Log.d("T", "len is "+Integer.toString(arr.length()));
            JSONObject address_components = arr.getJSONObject(0);
            JSONArray addr_2 = address_components.optJSONArray("address_components");
            String samp = addr_2.getJSONObject(0).getString("long_name");
            Log.d("T", "sample is "+samp);
            for (int k=0; k<addr_2.length(); k++) {
                JSONObject obj = addr_2.getJSONObject(k);
                if (obj.getString("long_name").contains("County")) {
                    county = obj.getString("long_name");
                    state = addr_2.getJSONObject(k+1).getString("short_name");
                }
            }
            Log.d("T", "location found by zipcode: " + county + ", " + state);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("T", "cannot find " + county + ", " + state);
        }

        try {
            JSONArray objarray = new JSONArray(loadJSONFromAsset());
            for (int k=0; k<objarray.length(); k++) {
                JSONObject obje = objarray.getJSONObject(k);
                //Log.d("T", "samp are " + obje.getString("county-name"));
                if (county!=null) {
                    String[] con = county.split(" ");
                    if (obje.getString("county-name").contains(con[0])) {
                        obama = obje.getString("obama-percentage");
                        romney = obje.getString("romney-percentage");
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setvaluestolayout(n, candidates);

    }

    public void setvaluestolayout(int n, ArrayList<JSONArray> candidates) {
        //TextView t1 = (TextView) findViewById(R.id.txt1);
        TextView t11 = (TextView) findViewById(R.id.txt11);
        TextView t12 = (TextView) findViewById(R.id.txt12);
        TextView t13 = (TextView) findViewById(R.id.txt13);
        TextView t14 = (TextView) findViewById(R.id.txt14);
        TextView t15 = (TextView) findViewById(R.id.txt15);
        //TextView t2 = (TextView) findViewById(R.id.txt2);
        TextView t21 = (TextView) findViewById(R.id.txt21);
        TextView t22 = (TextView) findViewById(R.id.txt22);
        TextView t23 = (TextView) findViewById(R.id.txt23);
        TextView t24 = (TextView) findViewById(R.id.txt24);
        TextView t25 = (TextView) findViewById(R.id.txt25);
        //TextView t3 = (TextView) findViewById(R.id.txt3);
        TextView t31 = (TextView) findViewById(R.id.txt31);
        TextView t32 = (TextView) findViewById(R.id.txt32);
        TextView t33 = (TextView) findViewById(R.id.txt33);
        TextView t34 = (TextView) findViewById(R.id.txt34);
        TextView t35 = (TextView) findViewById(R.id.txt35);
        //TextView t4 = (TextView) findViewById(R.id.txt4);
        TextView t41 = (TextView) findViewById(R.id.txt41);
        TextView t42 = (TextView) findViewById(R.id.txt42);
        TextView t43 = (TextView) findViewById(R.id.txt43);
        TextView t44 = (TextView) findViewById(R.id.txt44);
        TextView t45 = (TextView) findViewById(R.id.txt45);
        TextView t5 = (TextView) findViewById(R.id.textV);
        ImageView i1= (ImageView) findViewById(R.id.image1);
        ImageView i2 = (ImageView) findViewById(R.id.image2);
        ImageView i3 = (ImageView) findViewById(R.id.image3);
        ImageView i4 = (ImageView) findViewById(R.id.image4);

        if (n == 3) {

            try {
                person1 = candidates.get(0).getJSONObject(0);
                dist = person1.getString("state_name");
                firstname1 = person1.getString("first_name");
                lasttname1 = person1.getString("last_name");
                partyname1 = person1.getString("party");
                email1 = person1.getString("oc_email");
                website1 = person1.getString("website");
                title1 = person1.getString("title");

                person2 = candidates.get(0).getJSONObject(1);
                firstname2 = person2.getString("first_name");
                lasttname2 = person2.getString("last_name");
                partyname2 = person2.getString("party");
                email2 = person2.getString("oc_email");
                website2 = person2.getString("website");
                title2 = person2.getString("title");

                person3 = candidates.get(0).getJSONObject(2);
                firstname3 = person3.getString("first_name");
                lasttname3 = person3.getString("last_name");
                partyname3 = person3.getString("party");
                email3 = person3.getString("oc_email");
                website3 = person3.getString("website");
                title3 = person3.getString("title");

                if (partyname1.equals("D")) {
                    partyname1 = "Democrat";
                } else if (partyname1.equals("R")) {
                    partyname1 = "Republican";
                } else {
                    partyname1 = "Independent";
                }

                if (partyname2.equals("D")) {
                    partyname2 = "Democrat";
                } else if (partyname2.equals("R")) {
                    partyname2 = "Republican";
                } else {
                    partyname2 = "Independent";
                }


                if (partyname3.equals("D")) {
                    partyname3 = "Democrat";
                } else if (partyname3.equals("R")) {
                    partyname3 = "Republican";
                } else {
                    partyname3 = "Independent";
                }
                if (county!=null) {
                    t5.setText(county +", "+state);
                } else {
                    t5.setText(dist);
                }
                Log.d("T", "titles " + title1+ title2+title3+title4);

                if (title1.equals("Sen")) {
                    //t1.setText(firstname1 + " " + lasttname1 + "\n" + partyname1 + "\n" + email1 + "\n" + website1+"\n"+"Tweet: "+tweet1);
                    t11.setText(firstname1 + " " + lasttname1);
                    t12.setText(partyname1);
                    t13.setText(email1);
                    t14.setText(website1);
                    t15.setText("Tweet: "+tweet1);
                    i1.setTag(im1);
                    new DownloadImageTask().execute(i1);
                } else if (title1.equals("Rep")) {
                    //t3.setText(firstname1 + " " + lasttname1 + "\n" + partyname1 + "\n" + email1 + "\n" + website1+"\n"+"Tweet: "+tweet1);
                    t31.setText(firstname1 + " " + lasttname1);
                    t32.setText(partyname1);
                    t33.setText(email1);
                    t34.setText(website1);
                    t35.setText("Tweet: "+tweet1);
                    i3.setTag(im1);
                    new DownloadImageTask().execute(i3);
                }

                if (title2.equals("Sen") && title1.equals("Sen")) {
                    //t2.setText(firstname2 + " " + lasttname2 + "\n" + partyname2 + "\n" + email2 + "\n" + website2+"\n"+"Tweet: "+tweet2);
                    t21.setText(firstname2 + " " + lasttname2);
                    t22.setText(partyname2);
                    t23.setText(email2);
                    t24.setText(website2);
                    t25.setText("Tweet: "+tweet2);
                    i2.setTag(im2);
                    new DownloadImageTask().execute(i2);
                } else if (title2.equals("Sen")) {
                    //t1.setText(firstname2 + " " + lasttname2 + "\n" + partyname2 + "\n" + email2 + "\n" + website2+"\n"+"Tweet: "+tweet2);
                    t11.setText(firstname2 + " " + lasttname2);
                    t12.setText(partyname2);
                    t13.setText(email2);
                    t14.setText(website2);
                    t15.setText("Tweet: "+tweet2);
                    i1.setTag(im2);
                    new DownloadImageTask().execute(i1);
                } else if (title2.equals("Rep")) {
                    //t3.setText(firstname2 + " " + lasttname2 + "\n" + partyname2 + "\n" + email2 + "\n" + website2+"\n"+"Tweet: "+tweet2);
                    t31.setText(firstname2 + " " + lasttname2);
                    t32.setText(partyname2);
                    t33.setText(email2);
                    t34.setText(website2);
                    t35.setText("Tweet: "+tweet2);
                    i3.setTag(im2);
                    new DownloadImageTask().execute(i3);
                }

                if (title3.equals("Sen") && title1.equals("Sen")) {
                    //t2.setText(firstname3 + " " + lasttname3 + "\n" + partyname3 + "\n" + email3 + "\n" + website3+"\n"+"Tweet: "+tweet3);
                    t21.setText(firstname3 + " " + lasttname3);
                    t22.setText(partyname3);
                    t23.setText(email3);
                    t24.setText(website3);
                    t25.setText("Tweet: "+tweet3);
                    i2.setTag(im3);
                    new DownloadImageTask().execute(i2);
                } else if (title3.equals("Sen") && title2.equals("Sen")) {
                    //t2.setText(firstname3 + " " + lasttname3 + "\n" + partyname3 + "\n" + email3 + "\n" + website3+"\n"+"Tweet: "+tweet3);
                    t21.setText(firstname3 + " " + lasttname3);
                    t22.setText(partyname3);
                    t23.setText(email3);
                    t24.setText(website3);
                    t25.setText("Tweet: "+tweet3);
                    i2.setTag(im3);
                    new DownloadImageTask().execute(i2);
                } else if (title3.equals("Rep")){
                    //t3.setText(firstname3 + " " + lasttname3 + "\n" + partyname3 + "\n" + email3 + "\n" + website3+"\n"+"Tweet: "+tweet3);
                    t31.setText(firstname3 + " " + lasttname3);
                    t32.setText(partyname3);
                    t33.setText(email3);
                    t34.setText(website3);
                    t35.setText("Tweet: "+tweet3);
                    i3.setTag(im3);
                    new DownloadImageTask().execute(i3);
                }

                String pl = n+" "+mess+" "+state+" "+county+" "+obama+" "+romney+" "+firstname1+ " "+lasttname1+" "+partyname1+" "+firstname2+ " "+lasttname2+" "+partyname2+" "+firstname3+ " "+lasttname3+" "+partyname3+" "+firstname4+ " "+lasttname4+" "+partyname4;
                Intent sendIntent = new Intent(getBaseContext(), PhonetoWatchService.class);
                sendIntent.putExtra("details", pl);
                startService(sendIntent);

            } catch (Exception e) {

            }

        } else if (n == 4) {

            try {

                person1 = candidates.get(0).getJSONObject(0);
                dist = person1.getString("state_name");

                firstname1 = person1.getString("first_name");
                lasttname1 = person1.getString("last_name");
                partyname1 = person1.getString("party");
                email1 = person1.getString("oc_email");
                website1 = person1.getString("website");
                title1 = person1.getString("title");

                person2 = candidates.get(0).getJSONObject(1);
                firstname2 = person2.getString("first_name");
                lasttname2 = person2.getString("last_name");
                partyname2 = person2.getString("party");
                email2 = person2.getString("oc_email");
                website2 = person2.getString("website");
                title2 = person2.getString("title");

                person3 = candidates.get(0).getJSONObject(2);
                firstname3 = person3.getString("first_name");
                lasttname3 = person3.getString("last_name");
                partyname3 = person3.getString("party");
                email3 = person3.getString("oc_email");
                website3 = person3.getString("website");
                title3 = person3.getString("title");

                person4 = candidates.get(0).getJSONObject(3);
                firstname4 = person4.getString("first_name");
                lasttname4 = person4.getString("last_name");
                partyname4 = person4.getString("party");
                email4 = person4.getString("oc_email");
                website4 = person4.getString("website");
                title4 = person4.getString("title");

                if (partyname1.equals("D")) {
                    partyname1 = "Democrat";
                } else if (partyname1.equals("R")) {
                    partyname1 = "Republican";
                } else {
                    partyname1 = "Independent";
                }

                if (partyname2.equals("D")) {
                    partyname2 = "Democrat";
                } else if (partyname2.equals("R")) {
                    partyname2 = "Republican";
                } else {
                    partyname2 = "Independent";
                }


                if (partyname3.equals("D")) {
                    partyname3 = "Democrat";
                } else if (partyname3.equals("R")) {
                    partyname3 = "Republican";
                } else {
                    partyname3 = "Independent";
                }

                if (partyname4.equals("D")) {
                    partyname4 = "Democrat";
                } else if (partyname4.equals("R")) {
                    partyname4 = "Republican";
                } else {
                    partyname4 = "Independent";
                }

                if (county!=null) {
                    t5.setText(county +", "+state);
                } else {
                    t5.setText(dist);
                }

                if (title1.equals("Sen")) {
                    //t1.setText(firstname1 + " " + lasttname1 + "\n" + partyname1 + "\n" + email1 + "\n" + website1+"\n"+"Tweet: "+tweet1);
                    t11.setText(firstname1 + " " + lasttname1);
                    t12.setText(partyname1);
                    t13.setText(email1);
                    t14.setText(website1);
                    t15.setText("Tweet: "+tweet1);
                    i1.setTag(im1);
                    new DownloadImageTask().execute(i1);
                } else if (title1.equals("Rep")) {
                    //t3.setText(firstname1 + " " + lasttname1 + "\n" + partyname1 + "\n" + email1 + "\n" + website1+"\n"+"Tweet: "+tweet1);
                    t31.setText(firstname1 + " " + lasttname1);
                    t32.setText(partyname1);
                    t33.setText(email1);
                    t34.setText(website1);
                    t35.setText("Tweet: "+tweet1);
                    i3.setTag(im1);
                    new DownloadImageTask().execute(i3);
                }

                if (title2.equals("Sen") && title1.equals("Sen")) {
                    //t2.setText(firstname2 + " " + lasttname2 + "\n" + partyname2 + "\n" + email2 + "\n" + website2+"\n"+"Tweet: "+tweet2);
                    t21.setText(firstname2 + " " + lasttname2);
                    t22.setText(partyname2);
                    t23.setText(email2);
                    t24.setText(website2);
                    t25.setText("Tweet: "+tweet2);
                    i2.setTag(im2);
                    new DownloadImageTask().execute(i2);
                } else if (title2.equals("Sen")) {
                    //t1.setText(firstname2 + " " + lasttname2 + "\n" + partyname2 + "\n" + email2 + "\n" + website2+"\n"+"Tweet: "+tweet2);
                    t11.setText(firstname2 + " " + lasttname2);
                    t12.setText(partyname2);
                    t13.setText(email2);
                    t14.setText(website2);
                    t15.setText("Tweet: "+tweet2);
                    i1.setTag(im2);
                    new DownloadImageTask().execute(i1);
                } else if (title2.equals("Rep") && title1.equals("Rep")) {
                    //t4.setText(firstname2 + " " + lasttname2 + "\n" + partyname2 + "\n" + email2 + "\n" + website2+"\n"+"Tweet: "+tweet2);
                    t41.setText(firstname2 + " " + lasttname2);
                    t42.setText(partyname2);
                    t43.setText(email2);
                    t44.setText(website2);
                    t45.setText("Tweet: "+tweet2);
                    i4.setTag(im2);
                    new DownloadImageTask().execute(i4);
                } else if (title2.equals("Rep")) {
                    //t3.setText(firstname2 + " " + lasttname2 + "\n" + partyname2 + "\n" + email2 + "\n" + website2+"\n"+"Tweet: "+tweet2);
                    t31.setText(firstname2 + " " + lasttname2);
                    t32.setText(partyname2);
                    t33.setText(email2);
                    t34.setText(website2);
                    t35.setText("Tweet: "+tweet2);
                    i3.setTag(im2);
                    new DownloadImageTask().execute(i3);
                }

                if (title3.equals("Sen") && title1.equals("Sen")) {
                    //t2.setText(firstname4 + " " + lasttname3 + "\n" + partyname3 + "\n" + email3 + "\n" + website3+"\n"+"Tweet: "+tweet3);
                    t21.setText(firstname3 + " " + lasttname3);
                    t22.setText(partyname3);
                    t23.setText(email3);
                    t24.setText(website3);
                    t25.setText("Tweet: "+tweet3);
                    i2.setTag(im3);
                    new DownloadImageTask().execute(i2);
                } else if (title3.equals("Sen") && title2.equals("Sen")) {
                    //t2.setText(firstname3 + " " + lasttname3 + "\n" + partyname3 + "\n" + email3 + "\n" + website3+"\n"+"Tweet: "+tweet3);
                    t21.setText(firstname3 + " " + lasttname3);
                    t22.setText(partyname3);
                    t23.setText(email3);
                    t24.setText(website3);
                    t25.setText("Tweet: "+tweet3);
                    i2.setTag(im3);
                    new DownloadImageTask().execute(i2);
                } else if (title3.equals("Sen")){
                    //t1.setText(firstname3 + " " + lasttname3 + "\n" + partyname3 + "\n" + email3 + "\n" + website3+"\n"+"Tweet: "+tweet3);
                    t11.setText(firstname3 + " " + lasttname3);
                    t12.setText(partyname3);
                    t13.setText(email3);
                    t14.setText(website3);
                    t15.setText("Tweet: "+tweet3);
                    i1.setTag(im3);
                    new DownloadImageTask().execute(i1);
                } else if (title3.equals("Rep") && title1.equals("Rep")) {
                    //t4.setText(firstname3 + " " + lasttname3 + "\n" + partyname3 + "\n" + email3 + "\n" + website3+"\n"+"Tweet: "+tweet3);
                    t41.setText(firstname3 + " " + lasttname3);
                    t42.setText(partyname3);
                    t43.setText(email3);
                    t44.setText(website3);
                    t45.setText("Tweet: "+tweet3);
                    i4.setTag(im3);
                    new DownloadImageTask().execute(i4);
                } else if (title3.equals("Rep") && title2.equals("Rep")) {
                    //t4.setText(firstname3 + " " + lasttname3 + "\n" + partyname3 + "\n" + email3 + "\n" + website3+"\n"+"Tweet: "+tweet3);
                    t41.setText(firstname3 + " " + lasttname3);
                    t42.setText(partyname3);
                    t43.setText(email3);
                    t44.setText(website3);
                    t45.setText("Tweet: "+tweet3);
                    i4.setTag(im3);
                    new DownloadImageTask().execute(i4);
                } else if (title3.equals("Rep")) {
                    //t3.setText(firstname3 + " " + lasttname3 + "\n" + partyname3 + "\n" + email3 + "\n" + website3+"\n"+"Tweet: "+tweet3);
                    t31.setText(firstname3 + " " + lasttname3);
                    t32.setText(partyname3);
                    t33.setText(email3);
                    t34.setText(website3);
                    t35.setText("Tweet: "+tweet3);
                    i3.setTag(im3);
                    new DownloadImageTask().execute(i3);
                }

                if (title4.equals("Sen") && title1.equals("Sen")) {
                    //t2.setText(firstname4 + " " + lasttname4 + "\n" + partyname4 + "\n" + email4 + "\n" + website4+"\n"+"Tweet: "+tweet4);
                    t21.setText(firstname4 + " " + lasttname4);
                    t22.setText(partyname4);
                    t23.setText(email4);
                    t24.setText(website4);
                    t25.setText("Tweet: "+tweet4);
                    i2.setTag(im4);
                    new DownloadImageTask().execute(i2);
                } else if (title4.equals("Sen") && title2.equals("Sen")) {
                    //t2.setText(firstname4 + " " + lasttname4 + "\n" + partyname4 + "\n" + email4 + "\n" + website4+"\n"+"Tweet: "+tweet4);
                    t21.setText(firstname4 + " " + lasttname4);
                    t22.setText(partyname4);
                    t23.setText(email4);
                    t24.setText(website4);
                    t25.setText("Tweet: "+tweet4);
                    i2.setTag(im4);
                    new DownloadImageTask().execute(i2);
                } else if (title4.equals("Sen") && title3.equals("Sen")) {
                    //t2.setText(firstname4 + " " + lasttname4 + "\n" + partyname4 + "\n" + email4 + "\n" + website4+"\n"+"Tweet: "+tweet4);
                    t21.setText(firstname4 + " " + lasttname4);
                    t22.setText(partyname4);
                    t23.setText(email4);
                    t24.setText(website4);
                    t25.setText("Tweet: "+tweet4);
                    i2.setTag(im4);
                    new DownloadImageTask().execute(i2);
                } else if (title4.equals("Rep") && title1.equals("Rep")) {
                    //t4.setText(firstname4 + " " + lasttname4 + "\n" + partyname4 + "\n" + email4 + "\n" + website4+"\n"+"Tweet: "+tweet4);
                    t41.setText(firstname4 + " " + lasttname4);
                    t42.setText(partyname4);
                    t43.setText(email4);
                    t44.setText(website4);
                    t45.setText("Tweet: "+tweet4);
                    i4.setTag(im4);
                    new DownloadImageTask().execute(i4);
                } else if (title4.equals("Rep") && title2.equals("Rep")) {
                    //t4.setText(firstname4 + " " + lasttname4 + "\n" + partyname4 + "\n" + email4 + "\n" + website4+"\n"+"Tweet: "+tweet4);
                    t41.setText(firstname4 + " " + lasttname4);
                    t42.setText(partyname4);
                    t43.setText(email4);
                    t44.setText(website4);
                    t45.setText("Tweet: "+tweet4);
                    i4.setTag(im4);
                    new DownloadImageTask().execute(i4);
                } else if (title4.equals("Rep") && title3.equals("Rep")) {
                    //t4.setText(firstname4 + " " + lasttname4 + "\n" + partyname4 + "\n" + email4 + "\n" + website4+"\n"+"Tweet: "+tweet4);
                    t41.setText(firstname4 + " " + lasttname4);
                    t42.setText(partyname4);
                    t43.setText(email4);
                    t44.setText(website4);
                    t45.setText("Tweet: "+tweet4);
                    i4.setTag(im4);
                    new DownloadImageTask().execute(i4);
                }

                Log.d("T", "first name" + firstname1);
                String pl = n+" "+mess+" "+state+" "+county+" "+obama+" "+romney+" "+firstname1+ " "+lasttname1+" "+partyname1+" "+firstname2+ " "+lasttname2+" "+partyname2+" "+firstname3+ " "+lasttname3+" "+partyname3+" "+firstname4+ " "+lasttname4+" "+partyname4;
                Intent sendIntent = new Intent(getBaseContext(), PhonetoWatchService.class);
                sendIntent.putExtra("details", pl);
                startService(sendIntent);

            } catch (Exception e) {

            }

        }




    }

    public void getdetail1 (View view) {
        TextView t11 = (TextView) findViewById(R.id.txt11);
        if (t11.getText().equals(firstname1 + " " + lasttname1)) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 0;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im1);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);
        }
        else if (t11.getText().equals(firstname2 + " " + lasttname2 )) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 1;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im2);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);

        } else if (t11.getText().equals(firstname3 + " " + lasttname3)) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 2;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im3);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);

        } else if (t11.getText().equals(firstname4 + " " + lasttname4 )) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 3;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im4);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);
        }

    }
    public void getdetail2 (View view) {
        TextView t22 = (TextView) findViewById(R.id.txt21);
        if (t22.getText().equals(firstname1 + " " + lasttname1)) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 0;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im1);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);
        }
        else if (t22.getText().equals(firstname2 + " " + lasttname2)) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 1;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im2);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);

        } else if (t22.getText().equals(firstname3 + " " + lasttname3 )) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 2;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im3);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);

        } else if (t22.getText().equals(firstname4 + " " + lasttname4 )) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 3;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im4);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);
        }

    }
    public void getdetail3 (View view) {
        TextView t33 = (TextView) findViewById(R.id.txt31);
        if (t33.getText().equals(firstname1 + " " + lasttname1 )) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 0;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im1);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);
        }
        else if (t33.getText().equals(firstname2 + " " + lasttname2 )) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 1;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im2);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);

        } else if (t33.getText().equals(firstname3 + " " + lasttname3)) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 2;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im3);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);

        } else if (t33.getText().equals(firstname4 + " " + lasttname4 )) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 3;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im4);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);
        }

    }

    public void getdetail4 (View view) {
        TextView t44 = (TextView) findViewById(R.id.txt41);
        if (t44.getText().equals(firstname1 + " " + lasttname1)) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 0;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im1);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);
        }
        else if (t44.getText().equals(firstname2 + " " + lasttname2 )) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 1;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im2);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);

        } else if (t44.getText().equals(firstname3 + " " + lasttname3 )) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 2;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im3);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);

        } else if (t44.getText().equals(firstname4 + " " + lasttname4 )) {
            Intent intentback = new Intent(this, Detail.class);
            Integer message = 3;
            intentback.putExtra("number", Integer.toString(message));
            intentback.putExtra("zip", mess);
            intentback.putExtra("im", im4);
            intentback.putExtra("yes", Integer.toString(10));
            startActivity(intentback);
        }

    }

}
