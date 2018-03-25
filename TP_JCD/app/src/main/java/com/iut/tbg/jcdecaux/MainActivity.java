package com.iut.tbg.jcdecaux;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.iut.tbg.jcdecaux.model.Contract;
import com.iut.tbg.jcdecaux.model.Station;

import java.util.ArrayList;
import java.util.List;

import static com.iut.tbg.jcdecaux.JSONAsyncTask.KEYCODE_GET_STATIONS_FROM_CONTRACT;
import static com.iut.tbg.jcdecaux.JSONAsyncTask.KEYCODE_LISTVIEW_STATIONS_FROM_CONTRACT;

public class MainActivity extends Activity {

    private Button btn_JSON;
    private TextView tv_JSON;
    private ListView lv_stations;

    //private JCDecaux database;
    private Contract contract;

    private ArrayList<Station> stations;
    private StationsAdapter stationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_JSON = (Button) findViewById(R.id.btn_JSON);
        //tv_JSON = (TextView) findViewById(R.id.tv_JSON);
        lv_stations = (ListView) findViewById(R.id.lv_stations);

        String[] citiesLyon = {"Caluire-et-Cuire", "Lyon", "Vaulx-En-Velin", "Villeurbanne"};
        contract = new Contract("Lyon", "Vélo'V", "FR", citiesLyon);

        stations = new ArrayList<>();
        stationsAdapter = new StationsAdapter(this, stations);
        lv_stations.setAdapter(stationsAdapter);

    }

    public void btn_json_click(View v) {

        // Keycode, URL, Textview
        //new JSONAsyncTask().execute(KEYCODE_GET_STATIONS_FROM_CONTRACT, "Lyon", tv_JSON);
        new JSONAsyncTask().execute(KEYCODE_LISTVIEW_STATIONS_FROM_CONTRACT, "Lyon", stations, stationsAdapter);



    }
}
