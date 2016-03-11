package com.example.alagu.wear;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alagu on 3/1/16.
 */
public class MyCardFragment extends Fragment {

    //private View fragmentView;
    private View.OnClickListener listener;
    private String t;
    private String te;
    private String pl;
    private String sc;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        //fragmentView = super.onCreateContentView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.fragment_my, container, false);
        TextView mTitle = (TextView) fragmentView.findViewById(R.id.title);
        TextView mDescription = (TextView) fragmentView.findViewById(R.id.description);
        TextView state = (TextView) fragmentView.findViewById(R.id.place);
        TextView scroll = (TextView) fragmentView.findViewById(R.id.scroll);
        mTitle.setText(t);
        mDescription.setText(te);
        state.setText(pl);
        scroll.setText(sc);
        fragmentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                if (listener != null) {
                    listener.onClick(view);
                }
            }

        });
        return fragmentView;
    }
    public void setTitle (String title) {
        t=title;
    }
    public String getTitle() {
        return t;
    }
    public void setplace (String place) {
        pl=place;
    }
    public void setscroll (String scr) {
        sc=scr;
    }

    public void setText (String text) {
        te=text;
    }
    public void setOnClickListener(final View.OnClickListener listener) {
        this.listener = listener;
    }

}