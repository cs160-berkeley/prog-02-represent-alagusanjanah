package com.example.alagu.wear;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Detail extends Activity {

    private String mess;
    private int num;
    private ArrayList<JSONArray> candidates;
    private ArrayList<JSONArray> comm;
    private ArrayList<JSONArray> bills;
    private JSONObject person;
    private String firstname;
    private String lasttname;
    private String partyname;
    private String title;
    private String memid;
    private String comm1="No committees found";
    private String comm2="";
    private String comm3="";
    private String comm4="";
    private String comm5="";
    private String bill1="No Bills found";
    private String bill2="";
    private String bill3="";
    private String bill4="";
    private String bill5="";
    private String start;
    private String end;
    private String na;
    private String im;



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent viewintent = getIntent();
        Bundle extras = viewintent.getExtras();
        if (extras.getString("yes")!=null) {
            mess = extras.getString("zip");
            String s = extras.getString("number");
            num = Integer.valueOf(s).intValue();
            im = extras.getString("im");
        } else {
            mess = extras.getString("zip");
            na = extras.getString("name");
            Log.d("T", "zip from phone listener " + mess +"and na is " +na);

        }

        String u = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=" + mess + "&apikey=a15f9cd6a6dd42eda09c80085cf4c69e";
        URLTask urltask = new URLTask();
        urltask.execute(u);
        try {
            candidates = urltask.get();
        } catch (Exception e) {
        }

        if (extras.getString("yes")!=null) {
            ImageView imv = (ImageView) findViewById(R.id.photo);
            imv.setTag(im);
            new DownloadImageTask().execute(imv);
            try {

                person = candidates.get(0).getJSONObject(num);
                firstname = person.getString("first_name");
                lasttname = person.getString("last_name");
                partyname = person.getString("party");
                title = person.getString("title");
                memid = person.getString("bioguide_id");
                start = person.getString("term_start");
                end = person.getString("term_end");
                if (partyname.equals("D")) {
                    partyname = "Democrat";
                } else if (partyname.equals("R")) {
                    partyname = "Republican";
                } else {
                    partyname = "Independent";
                }
            } catch (Exception e) {
            }
        } else {

            //Log.d("T", "trying " + "/"+candidates.get(0).getJSONObject(0).getString("first_name")+ " "+ candidates.get(0).getJSONObject(0).getString("lastt_name"));
            try {
                Log.d("T", "trying " + "/"+candidates.get(0).getJSONObject(0).getString("first_name")+ " "+ candidates.get(0).getJSONObject(0).getString("last_name"));
                if (("/"+candidates.get(0).getJSONObject(0).getString("first_name")+ " "+ candidates.get(0).getJSONObject(0).getString("last_name")).equals(na)) {
                    person = candidates.get(0).getJSONObject(0);
                    firstname = person.getString("first_name");
                    lasttname = person.getString("last_name");
                    partyname = person.getString("party");
                    title = person.getString("title");
                    memid = person.getString("bioguide_id");
                    start = person.getString("term_start");
                    end = person.getString("term_end");
                    if (partyname.equals("D")) {
                        partyname = "Democrat";
                    } else if (partyname.equals("R")) {
                        partyname = "Republican";
                    } else {
                        partyname = "Independent";
                    }
                } else if (("/"+candidates.get(0).getJSONObject(1).getString("first_name")+ " "+ candidates.get(0).getJSONObject(0).getString("last_name")).equals(na)) {
                    person = candidates.get(0).getJSONObject(1);
                    firstname = person.getString("first_name");
                    lasttname = person.getString("last_name");
                    partyname = person.getString("party");
                    title = person.getString("title");
                    memid = person.getString("bioguide_id");
                    start = person.getString("term_start");
                    end = person.getString("term_end");
                    if (partyname.equals("D")) {
                        partyname = "Democrat";
                    } else if (partyname.equals("R")) {
                        partyname = "Republican";
                    } else {
                        partyname = "Independent";
                    }
                } else if (("/"+candidates.get(0).getJSONObject(2).getString("first_name")+ " "+ candidates.get(0).getJSONObject(0).getString("last_name")).equals(na)) {
                    person = candidates.get(0).getJSONObject(2);
                    firstname = person.getString("first_name");
                    lasttname = person.getString("last_name");
                    partyname = person.getString("party");
                    title = person.getString("title");
                    memid = person.getString("bioguide_id");
                    start = person.getString("term_start");
                    end = person.getString("term_end");
                    if (partyname.equals("D")) {
                        partyname = "Democrat";
                    } else if (partyname.equals("R")) {
                        partyname = "Republican";
                    } else {
                        partyname = "Independent";
                    }
                } else if (("/"+candidates.get(0).getJSONObject(3).getString("first_name")+ " "+ candidates.get(0).getJSONObject(0).getString("last_name")).equals(na)) {
                    person = candidates.get(0).getJSONObject(3);
                    firstname = person.getString("first_name");
                    lasttname = person.getString("last_name");
                    partyname = person.getString("party");
                    title = person.getString("title");
                    memid = person.getString("bioguide_id");
                    start = person.getString("term_start");
                    end = person.getString("term_end");
                    if (partyname.equals("D")) {
                        partyname = "Democrat";
                    } else if (partyname.equals("R")) {
                        partyname = "Republican";
                    } else {
                        partyname = "Independent";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        String c = "https://congress.api.sunlightfoundation.com/committees?member_ids="+memid+"&apikey=a15f9cd6a6dd42eda09c80085cf4c69e";
        URLTask urltask1 = new URLTask();
        urltask1.execute(c);
        try {
            comm = urltask1.get();
        } catch (Exception e) {
        }

        try {
            if (comm.get(0).getJSONObject(0).getString("name")!=null) {
                comm1 = comm.get(0).getJSONObject(0).getString("name");
            }
            if (comm.get(0).getJSONObject(1).getString("name")!=null) {
                comm2 = comm.get(0).getJSONObject(1).getString("name");
            }
            if (comm.get(0).getJSONObject(2).getString("name")!=null) {
                comm3 = comm.get(0).getJSONObject(2).getString("name");
            }
            if (comm.get(0).getJSONObject(3).getString("name")!=null) {
                comm4 = comm.get(0).getJSONObject(3).getString("name");
            }if (comm.get(0).getJSONObject(4).getString("name")!=null) {
                comm5 = comm.get(0).getJSONObject(4).getString("name");
            }
            if (comm1== null) {
                comm1= " ";
            }
            if (comm2== null) {
                comm2= " ";
            }
            if (comm3== null) {
                comm3= " ";
            }
            if (comm4== null) {
                comm4= " ";
            }
            if (comm5== null) {
                comm5= " ";
            }


        } catch (Exception e) {
        }

        String b = "https://congress.api.sunlightfoundation.com/bills?sponsor_id="+memid+"&apikey=a15f9cd6a6dd42eda09c80085cf4c69e";
        URLTask urltask2 = new URLTask();
        urltask2.execute(b);
        try {
            bills = urltask2.get();
        } catch (Exception e) {
        }

        try {
            if (bills.get(0).getJSONObject(0).getString("short_title")!=null) {
                bill1 = bills.get(0).getJSONObject(0).getString("short_title");
            }
            if (bills.get(0).getJSONObject(1).getString("short_title")!=null) {
                bill2 = bills.get(0).getJSONObject(1).getString("short_title");
            }
            if (bills.get(0).getJSONObject(2).getString("short_title")!=null) {
                bill3 = bills.get(0).getJSONObject(2).getString("short_title");
            }
            if (bills.get(0).getJSONObject(3).getString("short_title")!=null) {
                bill4 = bills.get(0).getJSONObject(3).getString("short_title");
            }
            if (bills.get(0).getJSONObject(4).getString("short_title")!=null) {
                bill5 = bills.get(0).getJSONObject(4).getString("short_title");
            }
            if (bill1== null) {
                bill1=" ";
            }
            if (bill2== null) {
                bill2=" ";
            }
            if (bill3== null) {
                bill3=" ";
            }
            if (bill4== null) {
                bill4=" ";
            }
            if (bill5== null) {
                bill5=" ";
            }
        } catch (Exception e) {
        }

        TextView text1 = (TextView) findViewById(R.id.name);
        text1.setText(firstname + " " + lasttname);
        TextView text2 = (TextView) findViewById(R.id.party);
        text2.setText(partyname);
        TextView text3 = (TextView) findViewById(R.id.tiq);
        text3.setText("Term start: "+start);
        TextView text4 = (TextView) findViewById(R.id.time);
        text4.setText("Term end: "+end);
        TextView text5 = (TextView) findViewById(R.id.commitee);
        text5.setText(comm1+"\n"+"\n"+comm2+"\n"+"\n"+comm3+"\n"+"\n"+comm3+"\n"+"\n"+comm4+"\n"+"\n"+comm5);
        TextView text6 = (TextView) findViewById(R.id.bills);
        text6.setText(bill1+"\n"+"\n"+bill2+"\n"+"\n"+bill3+"\n"+"\n"+bill4+"\n"+"\n"+bill5);

    }
    public void gohome (View view) {
        // Do something in response to button
        Intent intentback = new Intent(this, MainActivity.class);

        startActivity(intentback);

    }

}