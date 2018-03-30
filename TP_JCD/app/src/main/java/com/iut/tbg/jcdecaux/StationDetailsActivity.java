package com.iut.tbg.jcdecaux;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.iut.tbg.jcdecaux.Models.Station;

import static com.iut.tbg.jcdecaux.MainActivity.KEY_STATION;

public class StationDetailsActivity extends Activity {

    protected TextView tv_station_number, tv_station_name, tv_station_address, tv_station_update_time;
    protected Station station;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_details);

        tv_station_number = (TextView) findViewById(R.id.tv_station_number);
        tv_station_name = (TextView) findViewById(R.id.tv_station_name);
        tv_station_address = (TextView) findViewById(R.id.tv_station_address);
        tv_station_update_time = (TextView) findViewById(R.id.tv_station_update_time);

        station = (Station) getIntent().getSerializableExtra(KEY_STATION);

        tv_station_number.setText(Integer.toString(station.getNumber()));
        tv_station_name.setText(station.getName());
        tv_station_address.setText(station.getAddress());
        tv_station_update_time.setText(station.getLast_update().toString());

    }
}
