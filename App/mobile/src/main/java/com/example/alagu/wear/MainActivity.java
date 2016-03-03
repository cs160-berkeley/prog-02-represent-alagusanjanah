package com.example.alagu.wear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
    //there's not much interesting happening. when the buttons are pressed, they start
    //the PhoneToWatchService with the cat name passed in.

    private Button zipButton;
    private Button locButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zipButton = (Button) findViewById(R.id.go1);
        locButton = (Button) findViewById(R.id.go2);

        zipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), PhonetoWatchService.class);
                sendIntent.putExtra("mode", "zipcode");
                startService(sendIntent);
                Intent viewintent = new Intent(getBaseContext(), CongView.class);
                viewintent.putExtra(CongView.EXTRA_MESSAGE, "z");
                startActivity(viewintent);
            }
        });

        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), PhonetoWatchService.class);
                sendIntent.putExtra("mode", "currentlocation");
                startService(sendIntent);
                Intent viewintent = new Intent(getBaseContext(), CongView.class);
                viewintent.putExtra(CongView.EXTRA_MESSAGE, "z");
                startActivity(viewintent);
            }
        });

    }


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
