package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson";

    //    ArrayList<Quake> earthquakes;
    private QuakeAdapter quakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.

//        earthquakes = QueryUtils.extractEarthquakes("");
//        earthquakes.add(new Quake("7.2" ,"San Francisco","2020"));
//        earthquakes.add(new Quake("5.1" ,"London" ,"2019"));
//        earthquakes.add(new Quake("2.3" ,"Tokyo" ,"2008"));
//        earthquakes.add(new Quake("8.4" ,"Mexico City" ,"2002"));
//        earthquakes.add(new Quake("6.4" ,"Moscow" ,"2015"));
//        earthquakes.add(new Quake("3.5" ,"Rio de Janeiro" ,"2017"));
//        earthquakes.add(new Quake("5.3" ,"Paris" ,"1998"));

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);
        // Create a new {@link ArrayAdapter} of earthquakes

        quakeAdapter = new QuakeAdapter(this, new ArrayList<Quake>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(quakeAdapter);

        // Start the AsyncTask to fetch the earthquake data


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current earthquake that was clicked on
                Quake currentEarthquake = quakeAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        EarthQuakeAsyncTask task = new EarthQuakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }


    private class EarthQuakeAsyncTask extends AsyncTask<String, Void, List<Quake>> {

        @Override
        protected List<Quake> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            return QueryUtils.fetchEarthquakeData(urls[0]);
        }

        @Override
        protected void onPostExecute(List<Quake> quakeList) {
            quakeAdapter.clear();
            // beware "  ! quakeList.isEmpty() "
            if (quakeList != null && !quakeList.isEmpty()) {
                quakeAdapter.addAll(quakeList);
            }
        }
    }
}