package com.iut.tbg.jcdecaux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.iut.tbg.jcdecaux.Adapters.ContractsAdapter;
import com.iut.tbg.jcdecaux.Adapters.StationsAdapter;
import com.iut.tbg.jcdecaux.Models.Contract;
import com.iut.tbg.jcdecaux.Models.Station;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.iut.tbg.jcdecaux.JCDecaux.API_KEY;
import static com.iut.tbg.jcdecaux.JCDecaux.KEY_ADAPTER_C;
import static com.iut.tbg.jcdecaux.JCDecaux.KEY_ADAPTER_S;
import static com.iut.tbg.jcdecaux.JCDecaux.KEY_CONTRACT;
import static com.iut.tbg.jcdecaux.JCDecaux.KEY_CONTRACTS;
import static com.iut.tbg.jcdecaux.JCDecaux.KEY_STATIONS;
import static com.iut.tbg.jcdecaux.JCDecaux.RQC_CONTRACTS;
import static com.iut.tbg.jcdecaux.JCDecaux.RQC_STATIONS_L;
import static com.iut.tbg.jcdecaux.JCDecaux.RQC_STATIONS_M;

public class JCDAsyncTask extends AsyncTask {

    /*
    public static final int KEYCODE_GET_CONTRACTS_ALL = 235;
    public static final int KEYCODE_GET_STATIONS_ALL = 246;
    public static final int KEYCODE_GET_STATIONS_FROM_CONTRACT = 257;
    public static final int KEYCODE_LISTVIEW_STATIONS_FROM_CONTRACT = 268;
    public static final int KEYCODE_MAP_STATIONS_FROM_CONTRACT = 279;
    */
    public static final int KEYCODE_GET_CONTRACTS_ALL_ACTIVITY = 191;
    public static final int KEYCODE_GET_STATIONS_MAP_ACTIVITY = 182;
    public static final int KEYCODE_GET_STATIONS_LIST_ACTIVITY = 173;

    protected int task;
    protected Context context;
    protected Contract contract;
    protected ArrayList list;
    protected ArrayAdapter adapter;
    protected SupportMapFragment map;

    JCDAsyncTask(Context callingActivity) {

        this.context = callingActivity;

    }

    @Override
    protected String doInBackground(Object... params) {

        //region 01 -- KEYCODE_GET_CONTRACTS_ALL_ACTIVITY

        /* Task #01 - Get the complete list of Contracts and launch related Activity
         *
         * @param int KEYCODE_GET_CONTRACTS_ALL_ACTIVITY    =>  params[0]
         * @param ArrayList<Contract> *contracts-list*      =>  params[1]
         *
         */
        if (params[0] instanceof Integer && (int) params[0] == KEYCODE_GET_CONTRACTS_ALL_ACTIVITY) {

            //new JCDAsyncTask(this).execute(KEYCODE_GET_CONTRACTS_ALL_ACTIVITY, databaseJCD.getContracts());
            this.task = KEYCODE_GET_CONTRACTS_ALL_ACTIVITY;

            String request = "https://api.jcdecaux.com/vls/v1/contracts?";
            request += "apiKey=" + API_KEY;

            String str_JSON = "{ \"error\" : \"Nothing has been done\" }";
            list = (ArrayList<Contract>) params[1];

            // On vide la liste avant de l'actualiser
            list.clear();

            //region GET_CONTRACTS_ALL : Récupération du fichier JSON
            try {

                URL url_JCD = new URL(request);
                HttpsURLConnection con_JCD = (HttpsURLConnection) url_JCD.openConnection();

                if (con_JCD.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(con_JCD.getInputStream()));
                    String aux = "";

                    StringBuilder builder = new StringBuilder();
                    while ((aux = in.readLine()) != null) {
                        builder.append(aux);
                    }

                    str_JSON = builder.toString();

                }

            } catch (Exception e) {

                str_JSON = "{ \"error\" : \"Exception :" + e.getMessage() + "\" }";

            }
            //endregion

            //region GET_CONTRACTS_ALL : Parsage du JSON
            try {

                JSONArray contractsJSON = new JSONArray(str_JSON);

                for (int i = 0; i < contractsJSON.length(); i++) {

                    JSONObject contractJSON = contractsJSON.getJSONObject(i);

                    JSONArray citiesJSON = contractJSON.getJSONArray("cities");
                    String[] citiesSTR = new String[citiesJSON.length()];
                    for (int j = 0; j < citiesJSON.length(); j++) { citiesSTR[j] = citiesJSON.getString(j); }

                    Contract contractOBJ = new Contract(
                            contractJSON.getString("name"),
                            contractJSON.getString("commercial_name"),
                            contractJSON.getString("country_code"),
                            citiesSTR);

                    // Mise à jour de la liste
                    list.add(contractOBJ);

                }

                str_JSON = "{ \"success\" : \"List updated and Adapter notified\" }";


            } catch (Exception e) {

                str_JSON = "{ \"error\" : \"Exception :" + e.getMessage() + "\" }";

            }
            //endregion

            return str_JSON;
        }

        //endregion

        //region 02 -- KEYCODE_GET_STATIONS_LIST_ACTIVITY

        /* Task #02 - Get the complete list of Stations of a Contract and launch related Activity
         *
         * @param int KEYCODE_GET_STATIONS_LIST_ACTIVITY    =>  params[0]
         * @param Contract *selected-contracts*             =>  params[1]
         *
         */
        if (params[0] instanceof Integer && (int) params[0] == KEYCODE_GET_STATIONS_LIST_ACTIVITY) {

            // new JCDAsyncTask(this).execute(KEYCODE_GET_STATIONS_LIST_ACTIVITY, databaseJCD.getContracts().get(selectedContract));
            this.task = KEYCODE_GET_STATIONS_LIST_ACTIVITY;
            contract = (Contract) params[1];

            String request = "https://api.jcdecaux.com/vls/v1/stations?";
            request += "contract=" + contract.getName();
            request += "&apiKey=" + API_KEY;

            String str_JSON = "{ \"error\" : \"Nothing has been done\" }";
            list = contract.getStations();

            // On vide la liste avant de l'actualiser
            list.clear();

            //region GET_STATIONS_FROM_CONTRACT : Récupération du fichier JSON
            try {

                URL url_JCD = new URL(request);
                HttpsURLConnection con_JCD = (HttpsURLConnection) url_JCD.openConnection();

                if (con_JCD.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(con_JCD.getInputStream()));
                    String aux = "";

                    StringBuilder builder = new StringBuilder();
                    while ((aux = in.readLine()) != null) {
                        builder.append(aux);
                    }

                    str_JSON = builder.toString();

                }

            } catch (Exception e) {

                str_JSON = "{ \"error\" : \"Exception :" + e.getMessage() + "\" }";

            }
            //endregion

            //region GET_STATIONS_FROM_CONTRACT : Parsage du JSON
            try {

                JSONArray stationsJSON = new JSONArray(str_JSON);

                for (int i = 0; i < stationsJSON.length(); i++) {

                    JSONObject stationJSON = stationsJSON.getJSONObject(i);

                    Station stationOBJ = new Station(
                            stationJSON.getInt("number"),
                            stationJSON.getString("name"),
                            stationJSON.getString("address"),
                            stationJSON.getJSONObject("position").getDouble("lat"),
                            stationJSON.getJSONObject("position").getDouble("lng"),
                            stationJSON.getBoolean("banking"),
                            stationJSON.getBoolean("bonus"));

                    stationOBJ.refresh(
                            stationJSON.getString("status"),
                            stationJSON.getInt("bike_stands"),
                            stationJSON.getInt("available_bike_stands"),
                            stationJSON.getInt("available_bikes"),
                            stationJSON.getLong("last_update"));

                    // Mise à jour de la liste
                    list.add(stationOBJ);

                }

                str_JSON = "{ \"success\" : \"List updated and Adapter notified\" }";


            } catch (Exception e) {

                str_JSON = "{ \"error\" : \"Exception :" + e.getMessage() + "\" }";

            }
            //endregion

            return str_JSON;
        }

        //endregion

        //region 03 -- KEYCODE_GET_STATIONS_MAP_ACTIVITY

        /* Task #03 - Get the complete list of Stations of a Contract and launch related Activity
         *
         * @param int KEYCODE_GET_STATIONS_MAP_ACTIVITY    =>  params[0]
         * @param Contract *selected-contracts*             =>  params[1]
         *
         */
        if (params[0] instanceof Integer && (int) params[0] == KEYCODE_GET_STATIONS_MAP_ACTIVITY) {

            // new JCDAsyncTask(this).execute(KEYCODE_GET_STATIONS_MAP_ACTIVITY, databaseJCD.getContracts().get(selectedContract));
            this.task = KEYCODE_GET_STATIONS_MAP_ACTIVITY;
            contract = (Contract) params[1];

            String request = "https://api.jcdecaux.com/vls/v1/stations?";
            request += "contract=" + contract.getName();
            request += "&apiKey=" + API_KEY;

            String str_JSON = "{ \"error\" : \"Nothing has been done\" }";
            list = contract.getStations();
            //adapter = (StationsAdapter) params[3];

            // On vide la liste avant de l'actualiser
            list.clear();

            //region GET_STATIONS_FROM_CONTRACT : Récupération du fichier JSON
            try {

                URL url_JCD = new URL(request);
                HttpsURLConnection con_JCD = (HttpsURLConnection) url_JCD.openConnection();

                if (con_JCD.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(con_JCD.getInputStream()));
                    String aux = "";

                    StringBuilder builder = new StringBuilder();
                    while ((aux = in.readLine()) != null) {
                        builder.append(aux);
                    }

                    str_JSON = builder.toString();

                }

            } catch (Exception e) {

                str_JSON = "{ \"error\" : \"Exception :" + e.getMessage() + "\" }";

            }
            //endregion

            //region GET_STATIONS_FROM_CONTRACT : Parsage du JSON
            try {

                JSONArray stationsJSON = new JSONArray(str_JSON);

                for (int i = 0; i < stationsJSON.length(); i++) {

                    JSONObject stationJSON = stationsJSON.getJSONObject(i);

                    Station stationOBJ = new Station(
                            stationJSON.getInt("number"),
                            stationJSON.getString("name"),
                            stationJSON.getString("address"),
                            stationJSON.getJSONObject("position").getDouble("lat"),
                            stationJSON.getJSONObject("position").getDouble("lng"),
                            stationJSON.getBoolean("banking"),
                            stationJSON.getBoolean("bonus"));

                    stationOBJ.refresh(
                            stationJSON.getString("status"),
                            stationJSON.getInt("bike_stands"),
                            stationJSON.getInt("available_bike_stands"),
                            stationJSON.getInt("available_bikes"),
                            stationJSON.getLong("last_update"));

                    // Mise à jour de la liste
                    list.add(stationOBJ);

                }

                str_JSON = "{ \"success\" : \"List updated and Adapter notified\" }";


            } catch (Exception e) {

                str_JSON = "{ \"error\" : \"Exception :" + e.getMessage() + "\" }";

            }

            //endregion

            return str_JSON;
        }

        //endregion

        return null;

    }

    @Override
    protected void onPostExecute(Object o) {

        if (o != null) {

            super.onPostExecute(o);

            switch(task) {

                case KEYCODE_GET_CONTRACTS_ALL_ACTIVITY:

                    Intent ContractsActivity = new Intent(context, MainActivity.class);
                    ContractsActivity.putExtra(KEY_CONTRACTS, list); // list = databaseJCD.getContracts()
                    ((Activity)context).startActivityForResult(ContractsActivity, RQC_CONTRACTS);

                    break;

                case KEYCODE_GET_STATIONS_LIST_ACTIVITY:

                    Intent StationsListActivity = new Intent(context, StationsListActivity.class);
                    //StationsListActivity.putExtra(KEY_STATIONS, list); // list = contract.getStations()
                    StationsListActivity.putExtra(KEY_CONTRACT, contract); // list = contract.getStations()
                    ((Activity)context).startActivityForResult(StationsListActivity, RQC_STATIONS_L);

                    break;

                case KEYCODE_GET_STATIONS_MAP_ACTIVITY:

                    Intent StationsMapActivity = new Intent(context, StationsMapActivity.class);
                    //StationsListActivity.putExtra(KEY_STATIONS, list); // list = contract.getStations()
                    StationsMapActivity.putExtra(KEY_CONTRACT, contract); // list = contract.getStations()
                    ((Activity)context).startActivityForResult(StationsMapActivity, RQC_STATIONS_M);

                    break;

                default:

                    //do something or nothing ? TODO

                    break;

            }

        }

        else { /* Exception ? Toast ? do something or nothing ? TODO */ }

    }

}
