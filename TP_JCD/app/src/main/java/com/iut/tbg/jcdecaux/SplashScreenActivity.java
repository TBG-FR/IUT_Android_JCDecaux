package com.iut.tbg.jcdecaux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.iut.tbg.jcdecaux.Adapters.ContractsAdapter;
import com.iut.tbg.jcdecaux.Models.Contract;

import java.util.ArrayList;

import static com.iut.tbg.jcdecaux.JCDAsyncTask.KEYCODE_GET_CONTRACTS_ALL_ACTIVITY;
import static com.iut.tbg.jcdecaux.JCDecaux.KEY_CONTRACTS;
import static com.iut.tbg.jcdecaux.JCDecaux.RQC_CONTRACTS;
import static com.iut.tbg.jcdecaux.JCDecaux.RQC_STATIONS_L;
import static com.iut.tbg.jcdecaux.JCDecaux.RQC_STATIONS_M;
import static com.iut.tbg.jcdecaux.MainActivity.KEY_CONTRACT;

public class SplashScreenActivity extends Activity {

    JCDecaux databaseJCD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /* TODO : Vérifier la source - Launcher, Contrats(List), Contrats(Maps) */

        /* TODO : IF FIRST_LAUNCH (NO CONTRACTS) ELSE IF RETURN (NO CONTRACTS) */

        databaseJCD = new JCDecaux();
        ContractsAdapter contractsAdapter = new ContractsAdapter(this, databaseJCD.getContracts());

        new JCDAsyncTask(this).execute(KEYCODE_GET_CONTRACTS_ALL_ACTIVITY, databaseJCD.getContracts(), contractsAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RQC_CONTRACTS && resultCode == RESULT_OK) {

            databaseJCD.setContracts((ArrayList<Contract>) data.getSerializableExtra(KEY_CONTRACTS));
            finish(); // TODO : backup to file in finish ?

            /*
            Intent ContractsActivity = new Intent(SplashScreenActivity.this, MainActivity.class);
            ContractsActivity.putExtra(KEY_CONTRACTS, databaseJCD.getContracts());
            startActivityForResult(ContractsActivity, RQC_CONTRACTS);
            */

        }

        else if(requestCode == RQC_STATIONS_L && resultCode == RESULT_OK) {

            Log.d("ActivityResult", "RQC_STATIONS_L");

            /*
            Intent ContractsActivity = new Intent(SplashScreenActivity.this, StationsListActivity.class);
            ContractsActivity.putExtra(KEY_CONTRACTS, databaseJCD.getContracts());
            startActivityForResult(ContractsActivity, RQC_CONTRACTS);
            */

        }

        else if(requestCode == RQC_STATIONS_M && resultCode == RESULT_OK) {

            Log.d("ActivityResult", "RQC_STATIONS_M");

            /*
            Intent ContractsActivity = new Intent(SplashScreenActivity.this, StationsMapActivity.class);
            ContractsActivity.putExtra(KEY_CONTRACTS, databaseJCD.getContracts());
            startActivityForResult(ContractsActivity, RQC_CONTRACTS);
            */

        }

    }

}