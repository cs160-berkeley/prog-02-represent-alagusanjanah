package com.example.alagu.wear;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CongView extends Activity {
    public final static String EXTRA_MESSAGE = "abcde";
    private String mess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong_view);

        Intent viewintent = getIntent();
        mess = viewintent.getStringExtra(EXTRA_MESSAGE);

        if (mess.equals("z")){
            TextView text1 = (TextView) findViewById(R.id.textV);
            text1.setText("Ohio's 7th District");
            TextView text2 = (TextView) findViewById(R.id.txt1);
            text2.setText("John White \nDemocrat \njohnwhite@g.com \nwww.jw.com \nTweet:Vote for me");
            TextView text3 = (TextView) findViewById(R.id.txt2);
            text3.setText("Bill West \nRepublican \nbillwest@g.com \nwww.bw.com \nTweet:Vote for me");
            TextView text4 = (TextView) findViewById(R.id.txt3);
            text4.setText("Amber Law \nRepublican \namb@g.com \nwww.al.com \nTweet:Vote for me");
            ImageView imv = (ImageView) findViewById(R.id.image1);
            imv.setImageResource(R.drawable.mr);
            ImageView imv2 = (ImageView) findViewById(R.id.image2);
            imv2.setImageResource(R.drawable.hs);
            ImageView imv3 = (ImageView) findViewById(R.id.image3);
            imv3.setImageResource(R.drawable.donna);

        }
        if (mess.equals("l")){
            TextView text1 = (TextView) findViewById(R.id.textV);
            text1.setText("Oregon's 2nd District");
            TextView text2 = (TextView) findViewById(R.id.txt1);
            text2.setText("Jessica Park \nDemocrat \njesspark@g.com \nwww.jesspark.com \nTweet:Vote for me");
            TextView text3 = (TextView) findViewById(R.id.txt2);
            text3.setText("Ryan Jack \nRepublican \nryanj@g.com \nwww.rj.com \nTweet:Vote for me");
            TextView text4 = (TextView) findViewById(R.id.txt3);
            text4.setText("Tony Chris \nRepublican \ntony@g.com \nwww.tc.com \nTweet:Vote for me");
            ImageView imv = (ImageView) findViewById(R.id.image1);
            imv.setImageResource(R.drawable.jess);
            ImageView imv2 = (ImageView) findViewById(R.id.image2);
            imv2.setImageResource(R.drawable.lewis);
            ImageView imv3 = (ImageView) findViewById(R.id.image3);
            imv3.setImageResource(R.drawable.ty);

        }

    }



    public void getdetailj (View view) {
        // Do something in response to button
        Intent intentback = new Intent(this, Detail.class);
        String message = "j";
        intentback.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentback);
    }
    public void getdetailh (View view) {
        // Do something in response to button
        Intent intentback = new Intent(this, Detail.class);
        String message = "h";
        intentback.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentback);
    }
    public void getdetaild (View view) {
        // Do something in response to button
        Intent intentback = new Intent(this, Detail.class);
        String message = "d";
        intentback.putExtra(EXTRA_MESSAGE, message);
        startActivity(intentback);
    }

}
