package com.example.alagu.wear;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class getvalues extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "abcde";
    private ArrayList<JSONArray> candidates;
    private JSONObject person1;
    private JSONObject person2;
    private JSONObject person3;
    private JSONObject person4;

    private String twitterid1;

    private String twitterid2;

    private String twitterid3;

    private String twitterid4;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        Intent viewintent = getIntent();
        Bundle extras = viewintent.getExtras();
        mess = extras.getString("place");
        Log.d("T", "got value " + mess);

        String u = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=" + mess + "&apikey=a15f9cd6a6dd42eda09c80085cf4c69e";
        URLTask urltask = new URLTask();
        urltask.execute(u);
        try {
            candidates = urltask.get();
        } catch (Exception e) {
        }
        //twit twitt = new twit();
        //twitt.execute(twitterid1);
        try {
            //JSONObject number = candidates.get(0).getJSONObject(4);
            n = urltask.getcountofcandidates();
            person1 = candidates.get(0).getJSONObject(0);
            twitterid1 = person1.getString("twitter_id");
            person2 = candidates.get(0).getJSONObject(1);
            twitterid2 = person2.getString("twitter_id");
            person3 = candidates.get(0).getJSONObject(2);
            twitterid3 = person3.getString("twitter_id");
            if (n==4) {
                person4 = candidates.get(0).getJSONObject(3);
                twitterid4 = person4.getString("twitter_id");
            }
        } catch (Exception e) {
        }


        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();
        statusesService.userTimeline(null, twitterid1, 1, null, null, null, true, null, true, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                tweet1 = result.data.get(0).text;
                im1 = result.data.get(0).user.profileImageUrl;
                im1 = im1.replaceAll("normal", "bigger");
                TwitterApiClient twitterApiClient2 = TwitterCore.getInstance().getApiClient();
                StatusesService statusesService2 = twitterApiClient2.getStatusesService();
                statusesService2.userTimeline(null, twitterid2, 1, null, null, null, true, null, true, new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        tweet2 = result.data.get(0).text;
                        im2 = result.data.get(0).user.profileImageUrl;
                        im2 = im2.replaceAll("normal", "bigger");
                        TwitterApiClient twitterApiClient3 = TwitterCore.getInstance().getApiClient();
                        StatusesService statusesService3 = twitterApiClient3.getStatusesService();
                        statusesService3.userTimeline(null, twitterid3, 1, null, null, null, true, null, true, new Callback<List<Tweet>>() {
                            @Override
                            public void success(Result<List<Tweet>> result) {
                                tweet3 = result.data.get(0).text;
                                im3 = result.data.get(0).user.profileImageUrl;
                                im3 = im3.replaceAll("normal", "bigger");
                                if (n == 4) {
                                    TwitterApiClient twitterApiClient4 = TwitterCore.getInstance().getApiClient();
                                    StatusesService statusesService4 = twitterApiClient4.getStatusesService();
                                    statusesService4.userTimeline(null, twitterid4, 1, null, null, null, true, null, true, new Callback<List<Tweet>>() {
                                        @Override
                                        public void success(Result<List<Tweet>> result) {
                                            tweet4 = result.data.get(0).text;
                                            im4 = result.data.get(0).user.profileImageUrl;
                                            im4 = im4.replaceAll("normal", "bigger");
                                            Intent intent = new Intent(getBaseContext(), CongView.class);
                                            intent.putExtra("place", mess);
                                            intent.putExtra("t1", tweet1);
                                            intent.putExtra("t2", tweet2);
                                            intent.putExtra("t3", tweet3);
                                            intent.putExtra("t4", tweet4);
                                            intent.putExtra("i1", im1);
                                            intent.putExtra("i2", im2);
                                            intent.putExtra("i3", im3);
                                            intent.putExtra("i4", im4);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void failure(TwitterException e) {
                                        }
                                    });
                                } else {
                                    Intent intent = new Intent(getBaseContext(), CongView.class);
                                    intent.putExtra("place", mess);
                                    intent.putExtra("t1", tweet1);
                                    intent.putExtra("t2", tweet2);
                                    intent.putExtra("t3", tweet3);
                                    intent.putExtra("i1", im1);
                                    intent.putExtra("i2", im2);
                                    intent.putExtra("i3", im3);
                                    startActivity(intent);

                                }
                            }

                                @Override
                                public void failure (TwitterException e){
                                }
                            });


                        }

                        @Override
                        public void failure (TwitterException e){
                        }
                    });

                }

                @Override
                public void failure (TwitterException e){
                }
            });



        }

    }
