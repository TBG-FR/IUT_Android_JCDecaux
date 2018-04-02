package com.iut.tbg.jcdecaux;

import android.content.Intent;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iut.tbg.jcdecaux.Adapters.StationsAdapter;
import com.iut.tbg.jcdecaux.Models.City;
import com.iut.tbg.jcdecaux.Models.Contract;
import com.iut.tbg.jcdecaux.Models.Station;

import java.util.ArrayList;

import static android.support.constraint.R.id.parent;
import static com.iut.tbg.jcdecaux.JCDecaux.KEY_CONTRACT;
import static com.iut.tbg.jcdecaux.JCDecaux.KEY_STATION;
import static com.iut.tbg.jcdecaux.JCDecaux.RESULT_CLOSE;
import static com.iut.tbg.jcdecaux.JSONAsyncTask.KEYCODE_LISTVIEW_STATIONS_FROM_CONTRACT;
import static com.iut.tbg.jcdecaux.JSONAsyncTask.KEYCODE_MAP_STATIONS_FROM_CONTRACT;

public class StationsMapActivity extends FragmentActivity implements
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnMapLoadedCallback,
        OnMapReadyCallback {

//    //region Inner Class - Customized InfoWindowAdapter
//
//    /** Demonstrates customizing the info window and/or its contents. */
//    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
//
//        // These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
//        // "title" and "snippet".
//        private final View mWindow;
//
//        private final View mContents;
//
//        CustomInfoWindowAdapter() {
//            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
//            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
//        }
//
//        @Override
//        public View getInfoWindow(Marker marker) {
//            if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_window) {
//                // This means that getInfoContents will be called.
//                return null;
//            }
//            render(marker, mWindow);
//            return mWindow;
//        }
//
//        @Override
//        public View getInfoContents(Marker marker) {
//            if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_contents) {
//                // This means that the default info contents will be used.
//                return null;
//            }
//            render(marker, mContents);
//            return mContents;
//        }
//
//        private void render(Marker marker, View view) {
//            int badge;
//            // Use the equals() method on a Marker to check for equals.  Do not use ==.
//            if (marker.equals(mBrisbane)) {
//                badge = R.drawable.badge_qld;
//            } else if (marker.equals(mAdelaide)) {
//                badge = R.drawable.badge_sa;
//            } else if (marker.equals(mSydney)) {
//                badge = R.drawable.badge_nsw;
//            } else if (marker.equals(mMelbourne)) {
//                badge = R.drawable.badge_victoria;
//            } else if (marker.equals(mPerth)) {
//                badge = R.drawable.badge_wa;
//            } else if (marker.equals(mDarwin1)) {
//                badge = R.drawable.badge_nt;
//            } else if (marker.equals(mDarwin2)) {
//                badge = R.drawable.badge_nt;
//            } else if (marker.equals(mDarwin3)) {
//                badge = R.drawable.badge_nt;
//            } else if (marker.equals(mDarwin4)) {
//                badge = R.drawable.badge_nt;
//            } else {
//                // Passing 0 to setImageResource will clear the image view.
//                badge = 0;
//            }
//            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
//
//            String title = marker.getTitle();
//            TextView titleUi = ((TextView) view.findViewById(R.id.title));
//            if (title != null) {
//                // Spannable string allows us to edit the formatting of the text.
//                SpannableString titleText = new SpannableString(title);
//                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
//                titleUi.setText(titleText);
//            } else {
//                titleUi.setText("");
//            }
//
//            String snippet = marker.getSnippet();
//            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
//            if (snippet != null && snippet.length() > 12) {
//                SpannableString snippetText = new SpannableString(snippet);
//                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
//                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
//                snippetUi.setText(snippetText);
//            } else {
//                snippetUi.setText("");
//            }
//        }
//    }
//
//    //endregion

    private GoogleMap mMap;
    private Spinner sp_city_selector;
    private ArrayList<Marker> markers;

    private Contract contract;
    //private StationsAdapter stationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stations_map);

        sp_city_selector = (Spinner)findViewById(R.id.sp_city_selector);
        contract = (Contract) getIntent().getSerializableExtra(KEY_CONTRACT);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //region TODO : City Selector (Select only some Stations, the ones related to one of the Cities of the Contract

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
                        // Whatever you want to happen when the third item gets selected
                        break;

                }
            }
        });
        */

        //endregion

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
        mMap.setOnMapLoadedCallback(this);
        markers = new ArrayList<>();

        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

        /* TODO : IMPROVE */

        if(contract.getStations().isEmpty() == false) {

            // Création des marqueurs
            for (Station station : contract.getStations()) {

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(station.getLatitude(), station.getLongitude()))
                        .title(station.getName())
                        .snippet(station.getAddress() + "\n" + station.getAvailable_bike() + " - " + station.getAvailable_bike_stands())
                        .draggable(false)
                );
                marker.setTag(station);

                markers.add(marker);

            }

        }

        //mMap.setInfoWindowAdapter(new Custom); TODO
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnInfoWindowLongClickListener(this);

    }

    @Override
    public void onMapLoaded() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Marker m : markers) { builder.include(m.getPosition()); }
        LatLngBounds bounds =  builder.build();

        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));

    }

    /** Called when the user clicks a marker. */
    /*
    public boolean onMarkerClick(final Marker marker) {

        // TODO ?

        return false;
    }*/

    @Override
    public void onInfoWindowClick(Marker marker) {

        // TODO ?

    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {

        Intent stationDetails = new Intent(StationsMapActivity.this, StationDetailsActivity.class);
        stationDetails.putExtra(KEY_STATION, (Station) marker.getTag());
        startActivity(stationDetails);

    }

    @Override
    public void finish() {

        Intent closeApp = new Intent();
        setResult(RESULT_CLOSE, closeApp);
        closeApp.putExtra(KEY_CONTRACT, contract);
        super.finish();

    }
}
