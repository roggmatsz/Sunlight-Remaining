package com.roggmatz.sunlightremaining;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static String API_URL = "http://api.wunderground.com/api/518617b47554b0f3/astronomy/q/CA/San_Francisco.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView defaultText = (TextView) findViewById(R.id.message);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sunMessage;
                DownloadJsonTask jTask = new DownloadJsonTask(view.getContext());
                jTask.execute(API_URL);
                Sun sun = new Sun();
                try {
                    sun = jTask.get();
                }
                catch (Exception e) {
                    Log.e("MainActivity.class", "Error in getting DownloadJsonTask results. \n");
                }
                Snackbar.make(view, view.getContext().getString(R.string.main_success_snackbar_message), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sunMessage = "Today's sunrise is at " + sun.getSunrise('h') + ":" +
                          sun.getSunrise('m') + ".\n\n";
                sunMessage += "Tonight's sunset is at " + sun.getSunset('h') + ":" +
                          sun.getSunset('m') + ".\n";

                defaultText.setText(sunMessage);
            }
        });

        Button locationButton = (Button) findViewById(R.id.location_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View sampleView = v;
                TextView tv = (TextView) findViewById(R.id.location_text);
                CoarseLocator locationListener = new CoarseLocator(sampleView);
                LocationManager locationManager = (LocationManager) v.getContext().getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                tv.setText(locationListener.latitude + ", " + locationListener.longitude);
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
