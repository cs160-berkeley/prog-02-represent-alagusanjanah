package com.example.alagu.wear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*public class CandidateArrayAdapter extends BaseAdapter { 
    Context context; 
    String[] names; 
    String[] party; 
    int[] photos;   

    public CandidateArrayAdapter(Context context, String[] names, String[] party, int[] photos) { 
        this.context = context; 
        this.names = names; 
        this.party = party; 
        this.photos = photos; } 

    @Override 
    public View getView(int position, View convertView, ViewGroup parent) { 
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View catRow = inflater.inflate(R.layout.content_detail, parent, false); 
        TextView nameView = (TextView) catRow.findViewById(R.id.name); 
        TextView fullnessView = (TextView) catRow.findViewById(R.id.party); 
        ImageView pictureView = (ImageView) catRow.findViewById(R.id.photo); 
        nameView.setText(names[position]); 
        fullnessView.setText(party[position]); 
        pictureView.setImageResource(photos[position]); 
        //Attaching onClickListener
        catRow.setOnClickListener(new View.OnClickListener() { 
        public void onClick(View v) { 
            //Toast.makeText(context, catNames[position], Toast.LENGTH_SHORT).show(); 
            }}); 
            return catRow; 
    }

*/