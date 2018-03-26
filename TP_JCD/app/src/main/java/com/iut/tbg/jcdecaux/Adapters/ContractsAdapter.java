package com.iut.tbg.jcdecaux.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iut.tbg.jcdecaux.Models.Contract;
import com.iut.tbg.jcdecaux.R;

import java.util.ArrayList;

public class ContractsAdapter extends ArrayAdapter<Contract> {

    ArrayList<Contract> contracts;

    private class ContractHolder {
        //private String name;
        //private String commercial_name;
        //private String country_code;
        //private ArrayList<City> cities;
        //private ArrayList<Station> stations;
        public TextView name;
        public TextView cities;
        //public ImageView country;
        public TextView country;
    }

    public ContractsAdapter(Context context, ArrayList<Contract> contracts) {
        super(context, R.layout.item_contract, contracts);
        this.contracts = contracts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Contract contract = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null) { convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contract,parent, false); }

        // Retrieve the contractHolder object from tag
        ContractHolder contractHolder = (ContractHolder) convertView.getTag();

        if(contractHolder == null) {

            contractHolder = new ContractHolder();

            contractHolder.name = (TextView) convertView.findViewById(R.id.contract_name);
            contractHolder.cities = (TextView) convertView.findViewById(R.id.contract_cities);
            contractHolder.country = (TextView) convertView.findViewById(R.id.contract_country);
            //contractHolder.country = (ImageView) convertView.findViewById(R.id.contract_country);

            // Cache the contractHolder object inside the view
            convertView.setTag(contractHolder);
        }

        // Populate the data from the data object via the contractHolder object into the template view.
        contractHolder.name.setText(contract.getName());
        contractHolder.cities.setText(contract.getCitiesToString());
        contractHolder.country.setText(contract.getCountryCode());

        //convertView.setSelected(true);

        /*
        switch (country.getCountryCode()) {

            case "FR":
                contractHolder.country.setImageResource(R.mipmap.country_flag_FR);
                break;

            case "ES":
                contractHolder.country.setImageResource(R.mipmap.country_flag_ES);
                break;

            default:
                contractHolder.country.setImageResource(R.mipmap.country_flag_unknown);
                break;

        }
        */

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public int getCount() {

        return this.contracts.size();

    }

    @Override
    public Contract getItem(int i) {

        return this.contracts.get(i);

    }

    @Override
    public long getItemId(int i) {

        return this.contracts.indexOf(getItem(i));

    }
}