package com.iut.tbg.jcdecaux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.iut.tbg.jcdecaux.Adapters.ContractsAdapter;
import com.iut.tbg.jcdecaux.Models.Contract;

import java.util.ArrayList;

import static com.iut.tbg.jcdecaux.JSONAsyncTask.KEYCODE_GET_CONTRACTS_ALL;

public class MainActivity extends Activity {

    //region MainActivity - Attributes

    private Button btn_stations_map, btn_stations_list, btn_refresh, btn_exit;
    private TextView tv_contracts;
    private ListView lv_contracts;

    private Contract selectedContract;
    private ArrayList<Contract> contracts;
    private ContractsAdapter contractsAdapter;

    //endregion

    //region MainActivity : Intents - Keys & Request Codes

    static final String KEY_CONTRACT = "JCD_CONTRACT";
    static final String KEY_STATIONS = "JCD_STATIONS";
    static final String KEY_STATION = "JCD_STATION";

    static final int RQC_CONTRACT = 172;
    static final int RQC_STATIONS = 183;
    static final int RQC_STATION = 194;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_stations_map = (Button) findViewById(R.id.btn_stations_map);
        btn_stations_list = (Button) findViewById(R.id.btn_stations_list);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_exit = (Button) findViewById(R.id.btn_exit);

        tv_contracts = (TextView) findViewById(R.id.tv_contracts);
        lv_contracts = (ListView) findViewById(R.id.lv_contracts);

        contracts = new ArrayList<>();
        contractsAdapter = new ContractsAdapter(this, contracts);
        lv_contracts.setAdapter(contractsAdapter);

        lv_contracts.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                v.setSelected(true);
                //lv_contracts.setSelection(position);

                selectedContract = contracts.get(position);

            }
        });

        // Récupérer la liste une première fois
        btn_Refresh(new View(getApplicationContext()));

    }

    public void btn_StationsList(View v) {

        if(selectedContract != null) {

            // Launch the next Activity
            Intent myIntent = new Intent(MainActivity.this, StationsListActivity.class);
            myIntent.putExtra(KEY_CONTRACT, selectedContract);
            //startActivityForResult(myIntent, RQC_NAME);
            startActivity(myIntent);

        }

    }

    public void btn_StationsMap(View v) {

        if(selectedContract != null) {

            // Launch the next Activity
            Intent myIntent = new Intent(MainActivity.this, StationsMapActivity.class);
            myIntent.putExtra(KEY_CONTRACT, selectedContract);
            //startActivityForResult(myIntent, RQC_NAME);
            startActivity(myIntent);

        }

    }

    public void btn_Refresh(View v) {

        // Keycode, URL, Textview
        new JSONAsyncTask().execute(KEYCODE_GET_CONTRACTS_ALL, contracts, contractsAdapter);

    }

    public void btn_Exit(View v) {

        finish();

    }
}
