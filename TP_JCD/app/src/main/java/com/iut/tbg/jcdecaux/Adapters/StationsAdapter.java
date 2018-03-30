package com.iut.tbg.jcdecaux.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iut.tbg.jcdecaux.R;
import com.iut.tbg.jcdecaux.Models.Station;

import java.io.Serializable;
import java.util.ArrayList;

public class StationsAdapter extends ArrayAdapter<Station> implements Serializable {

    ArrayList<Station> stations;

    private class StationHolder {
        //private int number;
        //private String name;
        //private String address;
        //private double latitude;
        //private double longitude;
        //private boolean banking;
        //private boolean bonus;
        //private Status status;
        //private int bike_stands;
        //private int available_bike_stands;
        //private int available_bike;
        //private Timestamp last_update;
        public TextView name;
        public TextView address;
        public ImageView status;
    }

    public StationsAdapter(Context context, ArrayList<Station> stations) {
        super(context, R.layout.item_station, stations);
        this.stations = stations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Station station = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null) { convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_station,parent, false); }

        // Retrieve the stationHolder object from tag
        StationHolder stationHolder = (StationHolder) convertView.getTag();

        if(stationHolder == null) {

            stationHolder = new StationHolder();

            stationHolder.name = (TextView) convertView.findViewById(R.id.station_name);
            stationHolder.address = (TextView) convertView.findViewById(R.id.station_address);
            stationHolder.status = (ImageView) convertView.findViewById(R.id.station_status);

            // Optional - Others fields ?

            // Cache the stationHolder object inside the view
            convertView.setTag(stationHolder);
        }

        // Populate the data from the data object via the stationHolder object into the template view.
        stationHolder.name.setText(station.getName());
        stationHolder.address.setText(station.getAddress());

        switch (station.getStatusToString()) {

            case "OPEN":
                stationHolder.status.setImageResource(R.mipmap.status_online);
                break;

            case "CLOSE":
                stationHolder.status.setImageResource(R.mipmap.status_offline);
                break;

            case "UNKNOWN":
                stationHolder.status.setImageResource(R.mipmap.status_unknown);
                break;

            default:
                stationHolder.status.setImageResource(R.mipmap.status_unknown);
                break;

        }

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public int getCount() {

        return this.stations.size();

    }

    @Override
    public Station getItem(int i) {

        return this.stations.get(i);

    }

    @Override
    public long getItemId(int i) {

        return this.stations.indexOf(getItem(i));

    }
}