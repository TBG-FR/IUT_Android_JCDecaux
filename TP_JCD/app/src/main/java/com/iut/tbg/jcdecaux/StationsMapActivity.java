package com.iut.tbg.jcdecaux;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iut.tbg.jcdecaux.Adapters.StationsAdapter;
import com.iut.tbg.jcdecaux.Models.City;
import com.iut.tbg.jcdecaux.Models.Contract;
import com.iut.tbg.jcdecaux.Models.Station;

import static android.support.constraint.R.id.parent;
import static com.iut.tbg.jcdecaux.JSONAsyncTask.KEYCODE_LISTVIEW_STATIONS_FROM_CONTRACT;
import static com.iut.tbg.jcdecaux.MainActivity.KEY_CONTRACT;

public class StationsMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Spinner sp_city_selector;

    private Contract contract;
    //private StationsAdapter stationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region Maps Original Instructions
        setContentView(R.layout.activity_stations_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //endregion

        sp_city_selector = (Spinner)findViewById(R.id.sp_city_selector);

        contract = (Contract) getIntent().getSerializableExtra(KEY_CONTRACT);

        StationsAdapter stationsAdapter = new StationsAdapter(this, contract.getStations()); /* TODO : Do it differently */
        new JSONAsyncTask().execute(KEYCODE_LISTVIEW_STATIONS_FROM_CONTRACT, contract.getName(), contract.getStations(), stationsAdapter);

        /* TODO : Replace getCities */
        ArrayAdapter<City> citySelectorAdapter = new ArrayAdapter<City>(StationsMapActivity.this, android.R.layout.simple_spinner_item, contract.getCities());
        citySelectorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city_selector.setAdapter(citySelectorAdapter);

        /* TODO : Behavior
        sp_city_selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override // TODO : OnNothing ?
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        break;
                    case 2:
                        // Whatever you want to happen when the thrid item gets selected
                        break;

                }
            }
        });
        */

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

        /* TODO : IMPROVE */

        if(contract.getStations().isEmpty() == false) {

            for (Station station : contract.getStations()) {

                mMap.addMarker(new MarkerOptions().position(new LatLng(station.getLatitude(), station.getLongitude())).title(station.getName()));

            }

            Station first = contract.getStations().get(0);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(first.getLatitude(), first.getLongitude())));
            mMap.moveCamera(CameraUpdateFactory.zoomOut());

        }

    }
}
