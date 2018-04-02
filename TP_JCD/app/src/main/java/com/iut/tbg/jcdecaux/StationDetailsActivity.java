package com.iut.tbg.jcdecaux;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iut.tbg.jcdecaux.Models.Station;

import static com.iut.tbg.jcdecaux.JCDecaux.KEY_STATION;

public class StationDetailsActivity extends Activity {

    protected TextView tv_station_status,
            tv_station_number, tv_station_name, tv_station_address,
            tv_bikes_available, tv_stands_available, tv_stands_total, tv_last_update;

    protected ImageView iv_banking, iv_bonus;
    protected Station station;
    protected Button btn_goto, btn_goback;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_details);

        //region Binding Layout Elements

        //Status
        tv_station_status = (TextView) findViewById(R.id.tv_station_status);

        //Basic Information
        //tv_station_number = (TextView) findViewById(R.id.tv_station_number);
        tv_station_name = (TextView) findViewById(R.id.tv_station_name);
        tv_station_address = (TextView) findViewById(R.id.tv_station_address);

        //Banking and Bonus
        iv_banking = (ImageView) findViewById(R.id.iv_banking_value) ;
        iv_bonus = (ImageView) findViewById(R.id.iv_bonus_value) ;

        //Dynamic data
        tv_bikes_available = (TextView) findViewById(R.id.tv_bikes_available);
        tv_stands_available = (TextView) findViewById(R.id.tv_stands_available);
        tv_stands_total = (TextView) findViewById(R.id.tv_stands_total);
        tv_last_update = (TextView) findViewById(R.id.tv_last_update);

        btn_goto = (Button) findViewById(R.id.btn_goto) ;
        btn_goback = (Button) findViewById(R.id.btn_goback) ;

        //endregion

        station = (Station) getIntent().getSerializableExtra(KEY_STATION);

        //region Apply display changes based on Station data

        //Status
        switch(station.getStatus()) {

            case OPEN:
                tv_station_status.setBackgroundResource(R.drawable.bg_station_status_open);
                tv_station_status.setText(R.string.tv_station_status_open);
                break;

            case CLOSE:
                tv_station_status.setBackgroundResource(R.drawable.bg_station_status_closed);
                tv_station_status.setText(R.string.tv_station_status_close);
                break;

            case UNKNOWN:
                // go to default

            default:
                tv_station_status.setBackgroundResource(R.drawable.bg_station_status_unknown);
                tv_station_status.setText(R.string.tv_station_status_unknown);
                break;

        }

        //Basic Information
        //tv_station_number.setText(String.format("%03d",station.getNumber()));
        tv_station_name.setText(station.getName());
        tv_station_address.setText(station.getAddress());

        //Banking and Bonus
        if(station.isBanking()) { iv_banking.setImageResource(R.mipmap.ic_yes); } else { iv_banking.setImageResource(R.mipmap.ic_no); }
        if(station.isBonus()) { iv_bonus.setImageResource(R.mipmap.ic_yes); } else { iv_bonus.setImageResource(R.mipmap.ic_no); }

        //Dynamic data
        tv_bikes_available.setText(String.format("%d",station.getAvailableBike()));
        tv_stands_available.setText(String.format("%d",station.getAvailableBikeStands()));
        tv_stands_total.setText(String.format("%d",station.getBikeStands()));
        tv_last_update.setText(station.getLastUpdateToLongString());

        //endregion

    }

    public void btn_GoTo(View v) {

        //TODO: calcul d'itinéraire
        Toast.makeText(getApplicationContext(), "Fonctionnalité non implémentée...", Toast.LENGTH_LONG).show();

    }

    public void btn_GoBack(View v) {

        finish();

    }

}
