package com.example.alagu.wear;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

public class Detail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        String m = intent.getStringExtra(CongView.EXTRA_MESSAGE);

        if (m.equals("j")){
            TextView text1 = (TextView) findViewById(R.id.name);
            text1.setText("John White");
            ImageView imv = (ImageView) findViewById(R.id.photo);
            imv.setImageResource(R.drawable.mr);
            TextView text2 = (TextView) findViewById(R.id.party);
            text2.setText("Democrat");
            TextView text3 = (TextView) findViewById(R.id.tiq);
            text3.setText("Senator since Jan 5, 2007");
            TextView text4 = (TextView) findViewById(R.id.time);
            text4.setText("(Next Election in 2016)");
            TextView text5 = (TextView) findViewById(R.id.commitee);
            text5.setText("Financial Services");
            TextView text6 = (TextView) findViewById(R.id.bills);
            text6.setText("Pension Accounatility Act");
        }

        if (m.equals("h")){
            TextView text1 = (TextView) findViewById(R.id.name);
            text1.setText("Bill West");
            ImageView imv = (ImageView) findViewById(R.id.photo);
            imv.setImageResource(R.drawable.hs);
            TextView text2 = (TextView) findViewById(R.id.party);
            text2.setText("Republican");
            TextView text3 = (TextView) findViewById(R.id.tiq);
            text3.setText("Senator since Jan 4, 2011    ");
            TextView text4 = (TextView) findViewById(R.id.time);
            text4.setText("(Next Election in 2016)");
            TextView text5 = (TextView) findViewById(R.id.commitee);
            text5.setText("Tansportation");
            TextView text6 = (TextView) findViewById(R.id.bills);
            text6.setText("Regulatory Accounatility Act");
        }

        if (m.equals("d")){
            TextView text1 = (TextView) findViewById(R.id.name);
            text1.setText("Amber Law");
            ImageView imv = (ImageView) findViewById(R.id.photo);
            imv.setImageResource(R.drawable.donna);
            TextView text2 = (TextView) findViewById(R.id.party);
            text2.setText("Republican");
            TextView text3 = (TextView) findViewById(R.id.tiq);
            text3.setText("Senator since Jan 4, 2011    ");
            TextView text4 = (TextView) findViewById(R.id.time);
            text4.setText("(Next Election in 2016)");
            TextView text5 = (TextView) findViewById(R.id.commitee);
            text5.setText("Tansportation");
            TextView text6 = (TextView) findViewById(R.id.bills);
            text6.setText("Regulatory Accounatility Act");
        }

    }

}