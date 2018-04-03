package com.iut.tbg.jcdecaux;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.iut.tbg.jcdecaux.AsyncTasks.JCDAsyncTask;
import com.iut.tbg.jcdecaux.Models.Contract;
import com.iut.tbg.jcdecaux.Models.JCDecaux;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static com.iut.tbg.jcdecaux.AsyncTasks.JCDAsyncTask.KEYCODE_GET_CONTRACTS_ALL_ACTIVITY;
import static com.iut.tbg.jcdecaux.AsyncTasks.JCDAsyncTask.KEYCODE_GET_STATIONS_LIST_ACTIVITY;
import static com.iut.tbg.jcdecaux.AsyncTasks.JCDAsyncTask.KEYCODE_GET_STATIONS_MAP_ACTIVITY;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.KEY_CONTRACTS;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.RESULT_ASK_LIST;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.RESULT_ASK_MAP;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.RESULT_CLOSE;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.RQC_CONTRACTS;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.RQC_STATIONS_L;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.RQC_STATIONS_M;
import static com.iut.tbg.jcdecaux.Models.JCDecaux.KEY_CONTRACT;

public class SplashScreenActivity extends Activity {

    protected TextView tv_splash_txt;
    protected JCDecaux databaseJCD;

    protected Context _this = this;
    protected boolean offline_mode = false;

    File backup_file;
    String backup_path = "jcd_data.tmp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tv_splash_txt = findViewById(R.id.tv_splash_txt);

        //region SplashScreenActivity : Récupération/Création des données (JCDecaux<>--Contracts<>--Stations)

        // Backup file location
        backup_file = new File(getFilesDir() + backup_path);

        // Check if we have a backup file and try to restore Contact list from here, or create a new one
        if(databaseJCD == null) {

            if(backup_file.exists()) {

                try {

                    FileInputStream fis = new FileInputStream(backup_file);
                    ObjectInputStream ois = new ObjectInputStream(fis);

                    databaseJCD = (JCDecaux) ois.readObject();

                    ois.close();
                    fis.close();

                    // Add a Toast "Contact list restored !"
                    Toast toast = Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.msg_data_restore_success_content), Toast.LENGTH_SHORT);
                    toast.show();

                }

                catch (Exception e) {

                    // Create an empty Contact list to avoid further errors
                    databaseJCD = new JCDecaux();

                    // Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    String msg = getApplicationContext().getResources().getString(R.string.msg_data_restore_fail_content);
                    msg += "\n\r" + e.getLocalizedMessage();
                    msg += "\n\r" + e.getStackTrace();

                    // Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(msg).setTitle(R.string.msg_data_restore_fail_title);

                    // Add the button
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int id) { } });

                    // Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();

                    // Display the AlertDialog
                    dialog.show();

                }

            }

            else {

                databaseJCD = new JCDecaux();

                // Add a Toast "New Contact list created !"
                Toast toast = Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.msg_data_restore_empty_content), Toast.LENGTH_SHORT);
                toast.show();

            }

        }

        //endregion

        tv_splash_txt.setText("Chargement des Contrats...");

        //region SplashScreenActivity : Vérification de la Connexion et lancement de l'AsyncTask

        //new JCDAsyncTask(this).execute(KEYCODE_GET_CONTRACTS_ALL_ACTIVITY, databaseJCD.getContracts());

        if(isOnline(this)) { new JCDAsyncTask(this).execute(KEYCODE_GET_CONTRACTS_ALL_ACTIVITY, databaseJCD.getContracts(), true); /* Force update */ }
        else {

            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(getString(R.string.alertdial_offline_message)).setTitle(getString(R.string.alertdial_offline_title));

            // Add the buttons
            builder.setPositiveButton(getString(R.string.alertdial_offline_btn_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    offline_mode = true;
                    new JCDAsyncTask(_this).execute(KEYCODE_GET_CONTRACTS_ALL_ACTIVITY, databaseJCD.getContracts(), false, offline_mode);

                }
            });
            builder.setNegativeButton(getString(R.string.alertdial_offline_btn_retry), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    recreate();

                }
            });

            // Create the AlertDialog
            AlertDialog dialog = builder.create();

            dialog.show();

        }

        //endregion

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
                        tv_splash_txt.setText("Chargement de la liste des Stations de " + databaseJCD.getContracts().get(selectedContract).getName() + "...");
                        new JCDAsyncTask(this).execute(KEYCODE_GET_STATIONS_LIST_ACTIVITY, databaseJCD.getContracts().get(selectedContract), false, offline_mode);
                        break;

                    case RESULT_ASK_MAP:
                        selectedContract = data.getIntExtra(KEY_CONTRACT, -1);
                        // TODO IF -1 OR NULL
                        tv_splash_txt.setText("Chargement de la carte des Stations de " + databaseJCD.getContracts().get(selectedContract).getName() + "...");
                        new JCDAsyncTask(this).execute(KEYCODE_GET_STATIONS_MAP_ACTIVITY, databaseJCD.getContracts().get(selectedContract), false, offline_mode);
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
                        new JCDAsyncTask(this).execute(KEYCODE_GET_CONTRACTS_ALL_ACTIVITY, databaseJCD.getContracts(), false, offline_mode);
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
                        new JCDAsyncTask(this).execute(KEYCODE_GET_CONTRACTS_ALL_ACTIVITY, databaseJCD.getContracts(), false, offline_mode);
                        break;

                }

                break;

            //endregion

        }

    }

    @Override
    public void finish() {

        //region SplashScreenActivity : Sauvegarde des données

        boolean superfinish = true;

        // Backup the Contact list for ulterior usage
        try {

            backup_file.createNewFile();

            FileOutputStream fos = new FileOutputStream(backup_file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(databaseJCD);

            oos.close();
            fos.close();

        }

        catch (Exception e) {

            // Avoid closing the app (to display the error message)
            superfinish = false;

            // Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            String msg = getApplicationContext().getResources().getString(R.string.msg_data_backup_fail_content);
            msg += "\n\r" + e.getLocalizedMessage();
            msg += "\n\r" + e.getStackTrace();

            // Chain together various setter methods to set the dialog characteristics
            builder.setMessage(msg).setTitle(R.string.msg_data_backup_fail_title);

            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) { } });

            builder.setNegativeButton(R.string.finish, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) { superfinish(); } });

            // Get the AlertDialog from create()
            AlertDialog dialog = builder.create();

            // Display the AlertDialog
            dialog.show();

        }

        // End of Activity
        if(superfinish)
            super.finish();

        //endregion

    }

    public void superfinish() { super.finish(); }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}