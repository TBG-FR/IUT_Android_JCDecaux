package com.iut.tbg.jcdecaux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iut.tbg.jcdecaux.Adapters.ContractsAdapter;
import com.iut.tbg.jcdecaux.Models.Contract;

import java.util.ArrayList;

import static com.iut.tbg.jcdecaux.Models.JCDecaux.KEY_CONTRACT;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.KEY_CONTRACTS;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.RESULT_ASK_LIST;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.RESULT_ASK_MAP;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.RESULT_CLOSE;

public class ContractsListActivity extends Activity {

    //region ContractsListActivity - Attributes

    protected Button btn_stations_map, btn_stations_list, btn_refresh, btn_exit;
    protected TextView tv_contracts;
    protected ListView lv_contracts;

    protected Contract selectedContract;
    protected int selectedContractPos;
    protected ArrayList<Contract> contracts;
    protected ContractsAdapter contractsAdapter;

    //endregion

    //region ContractsListActivity : Intents - Keys & Request Codes

    /*
    static final String KEY_CONTRACT = "JCD_CONTRACT";
    static final String KEY_STATIONS = "JCD_STATIONS";
    static final String KEY_STATION = "JCD_STATION";

    static final int RQC_CONTRACT = 172;
    static final int RQC_STATIONS = 183;
    static final int RQC_STATION = 194;
    */

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contracts_list);

        btn_stations_map = (Button) findViewById(R.id.btn_stations_map);
        btn_stations_list = (Button) findViewById(R.id.btn_stations_list);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_exit = (Button) findViewById(R.id.btn_exit);

        tv_contracts = (TextView) findViewById(R.id.tv_contracts);
        lv_contracts = (ListView) findViewById(R.id.lv_contracts);

        contracts = (ArrayList<Contract>) getIntent().getSerializableExtra(KEY_CONTRACTS);
        contractsAdapter = new ContractsAdapter(this, contracts);
        lv_contracts.setAdapter(contractsAdapter);

        lv_contracts.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                v.setSelected(true);
                //lv_contracts.setSelection(position);

                selectedContract = contracts.get(position);
                selectedContractPos = position;

            }
        });

    }

    public void btn_StationsList(View v) {

        if(selectedContract != null) {

            Intent closeApp = new Intent();
            setResult(RESULT_ASK_LIST, closeApp);
            closeApp.putExtra(KEY_CONTRACTS, contracts);
            closeApp.putExtra(KEY_CONTRACT, selectedContractPos);
            super.finish();

        }

        else { Toast.makeText(getApplicationContext(), "Vous devez d'abord sélectionner une Station !", Toast.LENGTH_LONG).show(); }

    }

    public void btn_StationsMap(View v) {

        if(selectedContract != null) {

            Intent closeApp = new Intent();
            setResult(RESULT_ASK_MAP, closeApp);
            closeApp.putExtra(KEY_CONTRACTS, contracts);
            closeApp.putExtra(KEY_CONTRACT, selectedContractPos);
            super.finish();

        }

        else { Toast.makeText(getApplicationContext(), "Vous devez d'abord sélectionner une Station !", Toast.LENGTH_LONG).show(); }

    }

    public void btn_Refresh(View v) {

        // Keycode, URL, Textview
        // TODO
        //new JSONAsyncTask().execute(KEYCODE_GET_CONTRACTS_ALL, contracts, contractsAdapter);

    }

    public void btn_Exit(View v) { finish(); }

    @Override
    public void finish() {

        Intent closeApp = new Intent();
        setResult(RESULT_CLOSE, closeApp);
        closeApp.putExtra(KEY_CONTRACTS, contracts);
        super.finish();

    }
}
