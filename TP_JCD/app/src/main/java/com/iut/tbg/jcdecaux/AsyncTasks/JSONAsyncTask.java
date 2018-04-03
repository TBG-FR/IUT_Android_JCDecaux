package com.iut.tbg.jcdecaux.AsyncTasks;

import android.os.AsyncTask;
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

import static com.iut.tbg.jcdecaux.Models.JCDecaux.API_KEY;

public class JSONAsyncTask extends AsyncTask {

    public static final int KEYCODE_GET_CONTRACTS_ALL = 235;
    public static final int KEYCODE_GET_STATIONS_ALL = 246;
    public static final int KEYCODE_GET_STATIONS_FROM_CONTRACT = 257;
    public static final int KEYCODE_LISTVIEW_STATIONS_FROM_CONTRACT = 268;
    public static final int KEYCODE_MAP_STATIONS_FROM_CONTRACT = 279;

    protected TextView tv_Result;
    protected ArrayList list;
    protected ArrayAdapter adapter;
    protected SupportMapFragment map;
    protected Object _this;

    @Override
    protected String doInBackground(Object... params) {

        /* Task #01 - Get the list of Stations related to a Contract (JSON)
         *
         * @param int KEYCODE_GET_STATIONS_FROM_CONTRACT  => params[0]
         * @param String *contract-name*   => params[1]
         * @param TextView *result-textview*  => params[2]
         *
         */
        //region Task #01 - Get Stations from Contract (JSON -> TextView)
        if (params[0] instanceof Integer && (int) params[0] == KEYCODE_GET_STATIONS_FROM_CONTRACT) {

            String request = "https://api.jcdecaux.com/vls/v1/stations?";
            request += "contract=" + (String) params[1];
            request += "&apiKey=" + API_KEY;

            String str_JSON = "{ \"error\" : \"Nothing has been done\" }";
            tv_Result = (TextView) params[2];

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
                ArrayList<Station> stationsOBJ = new ArrayList<>();

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

                    stationsOBJ.add(stationOBJ);

                }

                String[] citiesLyon = {"Caluire-et-Cuire", "Lyon", "Vaulx-En-Velin", "Villeurbanne"};
                Contract contract = new Contract("Lyon", "Vélo'V", "FR", citiesLyon, stationsOBJ);
                str_JSON = contract.toString();


            } catch (Exception e) {

                str_JSON = "{ \"error\" : \"Exception :" + e.getMessage() + "\" }";

            }
            //endregion

            return str_JSON;
        }
        //endregion

        /* Task #02 - Get the list of Stations related to a Contract (JSON)
         * This one receive the list and the adapter, and updates them
         *
         * @param int KEYCODE_LISTVIEW_STATIONS_FROM_CONTRACT  => params[0]
         * @param String *contract-name*   => params[1]
         * @param ArrayList<Station> *stations-list*  => params[2]
         * @param StationsAdapter *stations-adapter*  => params[3]
         *
         */
        //region Task #02 - Get {ListView} Stations from Contract (JSON -> Model -> Adapter/ListView
        else if (params[0] instanceof Integer && (int) params[0] == KEYCODE_LISTVIEW_STATIONS_FROM_CONTRACT) {

            String request = "https://api.jcdecaux.com/vls/v1/stations?";
            request += "contract=" + (String) params[1];
            request += "&apiKey=" + API_KEY;

            String str_JSON = "{ \"error\" : \"Nothing has been done\" }";
            list = (ArrayList<Station>) params[2];
            adapter = (StationsAdapter) params[3];

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

        /* Task #03 - Get the full list of Contracts (JSON)
         *
         * @param int KEYCODE_GET_CONTRACTS_ALL  => params[0]
         * @param ArrayList<Station> *contracts-list*  => params[1]
         * @param ContractsAdapter *contracts-adapter*  => params[2]
         *
         */
        //region Task #03 - Get Contracts (JSON -> Model -> Adapter/ListView
        else if (params[0] instanceof Integer && (int) params[0] == KEYCODE_GET_CONTRACTS_ALL) {

            String request = "https://api.jcdecaux.com/vls/v1/contracts?";
            request += "apiKey=" + API_KEY;

            String str_JSON = "{ \"error\" : \"Nothing has been done\" }";
            list = (ArrayList<Contract>) params[1];
            adapter = (ContractsAdapter) params[2];

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

        /* Task #04 - Get the list of Stations related to a Contract (JSON)
         * This one receive the list and the map, and launch the map callback
         *
         * @param int KEYCODE_MAP_STATIONS_FROM_CONTRACT  => params[0]
         * @param String *contract-name*   => params[1]
         * @param ArrayList<Station> *stations-list*  => params[2]
         * @param SupportMapFragment *map-instance*  => params[3]
         *
         */
        //region Task #04 - Get {Map} Stations from Contract (JSON -> Model
        else if (params[0] instanceof Integer && (int) params[0] == KEYCODE_MAP_STATIONS_FROM_CONTRACT) {

            String request = "https://api.jcdecaux.com/vls/v1/stations?";
            request += "contract=" + (String) params[1];
            request += "&apiKey=" + API_KEY;

            String str_JSON = "{ \"error\" : \"Nothing has been done\" }";
            list = (ArrayList<Station>) params[2];
            map = (SupportMapFragment) params[3];
            _this = params[4];

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

            if (tv_Result != null) { tv_Result.setText((String) o); }
            else if (list != null && adapter != null) { adapter.notifyDataSetChanged(); }
            else if (list != null && map != null) { map.getMapAsync((OnMapReadyCallback)_this); }
            else { /* Log ? Toast ? */ }

        }

        else { /* Exception ? Toast ? */ }

    }

}
