package com.iut.tbg.jcdecaux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.iut.tbg.jcdecaux.Adapters.ContractsAdapter;
import com.iut.tbg.jcdecaux.Adapters.StationsAdapter;
import com.iut.tbg.jcdecaux.Models.Contract;

import java.util.ArrayList;

import static com.iut.tbg.jcdecaux.JCDAsyncTask.KEYCODE_GET_CONTRACTS_ALL_ACTIVITY;
import static com.iut.tbg.jcdecaux.JCDAsyncTask.KEYCODE_GET_STATIONS_LIST_ACTIVITY;
import static com.iut.tbg.jcdecaux.JCDAsyncTask.KEYCODE_GET_STATIONS_MAP_ACTIVITY;
import static com.iut.tbg.jcdecaux.JCDecaux.KEY_CONTRACTS;
import static com.iut.tbg.jcdecaux.JCDecaux.KEY_STATIONS;
import static com.iut.tbg.jcdecaux.JCDecaux.RESULT_ASK_LIST;
import static com.iut.tbg.jcdecaux.JCDecaux.RESULT_ASK_MAP;
import static com.iut.tbg.jcdecaux.JCDecaux.RESULT_CLOSE;
import static com.iut.tbg.jcdecaux.JCDecaux.RQC_CONTRACTS;
import static com.iut.tbg.jcdecaux.JCDecaux.RQC_STATIONS_L;
import static com.iut.tbg.jcdecaux.JCDecaux.RQC_STATIONS_M;
import static com.iut.tbg.jcdecaux.JCDecaux.KEY_CONTRACT;

public class SplashScreenActivity extends Activity {

    protected TextView tv_splash_txt;
    protected JCDecaux databaseJCD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tv_splash_txt = findViewById(R.id.tv_splash_txt);

        /* TODO : IF FIRST_LAUNCH (NO CONTRACTS) ELSE IF RETURN (NO CONTRACTS) */
        databaseJCD = new JCDecaux();

        tv_splash_txt.setText("Chargement des Contrats...");
        new JCDAsyncTask(this).execute(KEYCODE_GET_CONTRACTS_ALL_ACTIVITY, databaseJCD.getContracts());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        int selectedContract;
        Contract refreshedContract;

        switch(requestCode) {

            //region Contracts List related cases

            case RQC_CONTRACTS:

                databaseJCD.setContracts((ArrayList<Contract>) data.getSerializableExtra(KEY_CONTRACTS));

                switch(resultCode) {

                    // TODO : Case RESULT_OK, RESULT_CANCELED, etc and/or default Case ?

                    case RESULT_CLOSE:
                        finish();
                        break;

                    case RESULT_ASK_LIST:
                        selectedContract = data.getIntExtra(KEY_CONTRACT, -1);
                        // TODO IF -1 OR NULL
                        tv_splash_txt.setText("Chargement de la liste des Stations...");
                        new JCDAsyncTask(this).execute(KEYCODE_GET_STATIONS_LIST_ACTIVITY, databaseJCD.getContracts().get(selectedContract));
                        break;

                    case RESULT_ASK_MAP:
                        selectedContract = data.getIntExtra(KEY_CONTRACT, -1);
                        // TODO IF -1 OR NULL
                        tv_splash_txt.setText("Chargement de la carte des Stations...");
                        new JCDAsyncTask(this).execute(KEYCODE_GET_STATIONS_MAP_ACTIVITY, databaseJCD.getContracts().get(selectedContract));
                        break;

                }

                break;

            //endregion

            //region Stations List related cases

            case RQC_STATIONS_L:

                refreshedContract = (Contract) data.getSerializableExtra(KEY_CONTRACT);

                // TODO : Try to improve that part...
                for (Contract c :  databaseJCD.getContracts()) { if(c.getName() == refreshedContract.getName()) { c.setStations(refreshedContract.getStations()); } }

                switch(resultCode) {

                    // TODO : Case RESULT_OK, RESULT_CANCELED, etc and/or default Case ?

                    case RESULT_CLOSE:
                        tv_splash_txt.setText("Chargement des Contrats...");
                        new JCDAsyncTask(this).execute(KEYCODE_GET_CONTRACTS_ALL_ACTIVITY, databaseJCD.getContracts()/*, contractsAdapter*/);
                        break;

                }

                break;

            //endregion

            //region Stations Map related cases

            case RQC_STATIONS_M:

                refreshedContract = (Contract) data.getSerializableExtra(KEY_CONTRACT);

                // TODO : Try to improve that part...
                for (Contract c :  databaseJCD.getContracts()) { if(c.getName() == refreshedContract.getName()) { c.setStations(refreshedContract.getStations()); } }

                switch(resultCode) {

                    // TODO : Case RESULT_OK, RESULT_CANCELED, etc and/or default Case ?

                    case RESULT_CLOSE:
                        tv_splash_txt.setText("Chargement des Contrats...");
                        new JCDAsyncTask(this).execute(KEYCODE_GET_CONTRACTS_ALL_ACTIVITY, databaseJCD.getContracts());
                        break;

                }

                break;

            //endregion

        }

    }

    @Override
    public void finish() {

        // TODO : backup to file in finish ?
        super.finish();

    }
}