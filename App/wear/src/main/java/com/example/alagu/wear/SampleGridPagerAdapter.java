package com.example.alagu.wear;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridPagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SampleGridPagerAdapter extends FragmentGridPagerAdapter {
    private ArrayList<JSONArray> candidates;
    private int n;

    private JSONObject person1;
    private JSONObject person2;
    private JSONObject person3;
    private JSONObject person4;
    private String firstname1;
    private String lasttname1;
    private String partyname1;
    private String dist;
    private String twitterid1;
    private String firstname2;
    private String lasttname2;
    private String partyname2;
    private String twitterid2;
    private String firstname3;
    private String lasttname3;
    private String partyname3;
    private String twitterid3;
    private String firstname4;
    private String lasttname4;
    private String partyname4;
    private String twitterid4;
    private String[] words;




    public MyCardFragment fragment;
    Context mContext;
    private List mRows;
    int m = -1;
    private String place;

    public SampleGridPagerAdapter(Context ctx, FragmentManager fm, String mode) {
        super(fm);
        mContext = ctx;
        if (mode != null) {
            place = mode;
            words = place.split(" ");
            Log.d("T", "mode is in samp: " + words[0]);

        }
    }

    static final int[] BG_IMAGES = new int[] {
            R.drawable.rep
    };

    // A simple container for static data in each page
    private static class Page {
        String titleRes;
        String textRes;
        int iconRes;

        public Page(String titleRes, String textRes, int iconRes) {
            this.titleRes = titleRes;
            this.textRes = textRes;
            this.iconRes = iconRes;
        }
    }

    // Create a static set of pages in a 2D array
    private final Page[][] PAGES1 = {
            {
                    new Page("","", 0),
                    new Page("","", 0),
                    new Page("","", 0),
            },
            {
                    new Page("","", 0)
            }
    };
    private final Page[][] PAGES2 = {
            {
                    new Page("","", 0),
                    new Page("","", 0),
                    new Page("","", 0),
                    new Page("","", 0),
            },
            {
                    new Page("","", 0)
            }
    };
    /*private final Page[][] PAGES_EMPTY = {
            {
                    new Page(R.string.name0, R.string.party0, 0),
                    new Page(R.string.name0, R.string.party0, 0),
                    new Page(R.string.name0, R.string.party0, 0),
            },
            {
                    new Page(R.string.name0, R.string.party0, 0)
            }
    };*/

    // Override methods in FragmentGridPagerAdapter
    // Obtain the UI fragment at the specified position
    @Override
    public Fragment getFragment(int row, int col) {

        Page page;


        if (Integer.valueOf(words[0]).intValue() == 3) {
            if (words[3]!=null) {
                page = PAGES1[row][col];
                if (row == 0 && col == 0) {
                    page.titleRes = words[7] + " " + words[8];
                    page.textRes = words[9];
                } else if (row == 0 && col == 1) {
                    page.titleRes = words[10] + " " + words[11];
                    page.textRes = words[12];
                } else if (row == 0 && col == 2) {
                    page.titleRes = words[13] + " " + words[14];
                    page.textRes = words[15];
                } else if (row == 1 && col == 0) {
                    page.titleRes = words[3] + ", "+words[2];
                    page.textRes = " ";
                }
            } else {
                page = PAGES1[row][col];
                if (row == 0 && col == 0) {
                    page.titleRes = words[6] + " " + words[7];
                    page.textRes = words[8];
                } else if (row == 0 && col == 1) {
                    page.titleRes = words[9] + " " + words[10];
                    page.textRes = words[11];
                } else if (row == 0 && col == 2) {
                    page.titleRes = words[12] + " " + words[13];
                    page.textRes = words[14];
                } else if (row == 1 && col == 0 ) {
                    page.titleRes =" ";
                    page.textRes = " ";
                }
            }
            final MyCardFragment fragment = new MyCardFragment();
            fragment.setTitle(page.titleRes);
            fragment.setText(page.textRes);
            if (row!=1) {
                if (words[3]==null) {
                    fragment.setplace(" ");
                } else{
                    fragment.setplace(words[3] + ", " + words[2]);
                }
                fragment.setscroll("Scroll Up for 2012 Vote Stats");
            } else {
                if (words[3]==null) {
                    fragment.setplace(" ");
                    fragment.setscroll(" ");
                } else {

                    fragment.setText("Obama: " + words[5] + "% ");
                    fragment.setplace("Romney: " + words[6] + "% ");

                }
            }

            fragment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View view) {
                    Intent sendIntent = new Intent(mContext, WatchtoPhoneService.class);
                    String tit = fragment.getTitle();
                    sendIntent.putExtra("mode", tit);
                    sendIntent.putExtra("zip", words[1]);
                    mContext.startService(sendIntent);
                }

            });

            return fragment;

        } else if (Integer.valueOf(words[0]).intValue() == 4) {
            if (words[3]!=null) {
                page = PAGES1[row][col];
                if (row == 0 && col == 0) {
                    page.titleRes = words[7] + " " + words[8];
                    page.textRes = words[9];
                } else if (row == 0 && col == 1) {
                    page.titleRes = words[10] + " " + words[11];
                    page.textRes = words[12];
                } else if (row == 0 && col == 2) {
                    page.titleRes = words[13] + " " + words[14];
                    page.textRes = words[15];
                }else if (row == 0 && col == 3) {
                    page.titleRes = words[16] + " " + words[17];
                    page.textRes = words[18];
                } else if (row == 1 && col == 0) {
                    page.titleRes = " ";
                    page.textRes = " ";
                }
            } else {
                page = PAGES1[row][col];
                if (row == 0 && col == 0) {
                    page.titleRes = words[6] + " " + words[7];
                    page.textRes = words[8];
                } else if (row == 0 && col == 1) {
                    page.titleRes = words[9] + " " + words[10];
                    page.textRes = words[11];
                } else if (row == 0 && col == 2) {
                    page.titleRes = words[12] + " " + words[13];
                    page.textRes = words[14];
                } else if (row == 0 && col == 3) {
                    page.titleRes = words[15] + " " + words[16];
                    page.textRes = words[17];
                } else if (row == 1 && col == 0 ) {
                    page.titleRes = words[3]+", "+words[2];
                    page.textRes = " ";
                }
            }

            final MyCardFragment fragment = new MyCardFragment();
            fragment.setTitle(page.titleRes);
            fragment.setText(page.textRes);
            if (row!=1) {
                if (words[3]==null) {
                    fragment.setplace(" ");
                } else{
                    fragment.setplace(words[3] + ", " + words[2]);
                }
                fragment.setscroll("Scroll Up for 2012 Vote Stats");
            } else {
                if (words[3]==null) {
                    fragment.setplace(" ");
                    fragment.setscroll(" ");

                } else {
                    fragment.setText("Obama: " + words[5] + "% ");
                    fragment.setplace("Romney: " + words[6] + "% ");

                }
            }

            fragment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View view) {
                    Intent sendIntent = new Intent(mContext, WatchtoPhoneService.class);
                    String tit = fragment.getTitle();
                    sendIntent.putExtra("mode", tit);
                    sendIntent.putExtra("zip", words[1]);
                    mContext.startService(sendIntent);
                }

            });

            return fragment;


        }
        return null;
    }/*else {
            page = PAGES_EMPTY[row][col];
        }*/
        /*String title =
                page.titleRes != 0 ? mContext.getString(page.titleRes) : null;
        String text =
                page.textRes != 0 ? mContext.getString(page.textRes) : null;*/
        //CardFragment fragment = MyCardFragment.create(title, text, page.iconRes);


        // Advanced settings (card gravity, card expansion/scrolling)
        //fragment.setCardGravity(Gravity.BOTTOM);
        //fragment.setExpansionEnabled(true);
        //fragment.setExpansionDirection(CardFragment.EXPAND_DOWN);
        //fragment.setExpansionFactor(2.0f);
        //fragment.setCardMarginTop(150dp);


    // Obtain the background image for the specific page
    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        //TODO: fix demo purpose
        if (m == -1) {
            return GridPagerAdapter.BACKGROUND_NONE;
        } else if (m == 0) {
            if( row == 0 && column == 0) {
                return mContext.getResources().getDrawable(R.drawable.mr, null);
            } else if (row == 0 && column == 1) {
                return mContext.getResources().getDrawable(R.drawable.hs, null);
            } else if (row == 0 && column == 2) {
                return mContext.getResources().getDrawable(R.drawable.donna, null);
            } else if (row == 1 && column == 0) {
                return mContext.getResources().getDrawable(R.drawable.rep, null);
            } else {
                return GridPagerAdapter.BACKGROUND_NONE;
            }
        } else if (m == 1) {
            if( row == 0 && column == 0) {
                return mContext.getResources().getDrawable(R.drawable.jess, null);
            } else if (row == 0 && column == 1) {
                return mContext.getResources().getDrawable(R.drawable.lewis, null);
            } else if (row == 0 && column == 2) {
                return mContext.getResources().getDrawable(R.drawable.ty, null);
            } else if (row == 1 && column == 0) {
                return mContext.getResources().getDrawable(R.drawable.rep, null);
            } else {
                return GridPagerAdapter.BACKGROUND_NONE;
            }
        } else {
            return GridPagerAdapter.BACKGROUND_NONE;
        }
    }

    // Obtain the number of pages (vertical)
    @Override
    public int getRowCount() {
        return PAGES1.length;
    }

    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {
        return PAGES1[rowNum].length;
    }
}