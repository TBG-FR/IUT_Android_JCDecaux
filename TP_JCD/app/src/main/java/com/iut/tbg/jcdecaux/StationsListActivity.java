package com.iut.tbg.jcdecaux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.iut.tbg.jcdecaux.Adapters.StationsAdapter;
import com.iut.tbg.jcdecaux.Models.Contract;
import com.iut.tbg.jcdecaux.Models.Station;

import java.util.ArrayList;

import static com.iut.tbg.jcdecaux.JSONAsyncTask.KEYCODE_LISTVIEW_STATIONS_FROM_CONTRACT;
import static com.iut.tbg.jcdecaux.MainActivity.KEY_CONTRACT;
import static com.iut.tbg.jcdecaux.MainActivity.KEY_STATION;

public class StationsListActivity extends Activity {

    protected Button btn_refresh, btn_goback;
    protected ListView lv_stations;

    private Contract contract;
    private StationsAdapter stationsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations_list);

        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_goback = (Button) findViewById(R.id.btn_goback);
        lv_stations = (ListView) findViewById(R.id.lv_stations);

        contract = (Contract) getIntent().getSerializableExtra(KEY_CONTRACT);
        stationsAdapter = new StationsAdapter(this, contract.getStations());
        lv_stations.setAdapter(stationsAdapter);

        lv_stations.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Intent stationDetails = new Intent(StationsListActivity.this, StationDetailsActivity.class);
                stationDetails.putExtra(KEY_STATION, contract.getStations().get(position));
                startActivity(stationDetails);

            }
        });

        // Récupérer la liste une première fois
        btn_Refresh(new View(getApplicationContext()));

    }

    public void btn_Refresh(View v) {

        // Keycode, Contract, Stations (list), Adapter
        new JSONAsyncTask().execute(KEYCODE_LISTVIEW_STATIONS_FROM_CONTRACT, contract.getName(), contract.getStations(), stationsAdapter);

    }

    public void btn_GoBack(View v) {

        finish();

    }
}
