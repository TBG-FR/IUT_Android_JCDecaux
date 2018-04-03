package com.iut.tbg.jcdecaux.Models;

import com.iut.tbg.jcdecaux.Models.Contract;

import java.util.ArrayList;

/**
 * Stations de Lyon
 * https://api.jcdecaux.com/vls/v1/stations?contract=Lyon&apiKey=1409f7759c6065f255490f32441e1cc5bc8922da
 *
 * Contrats
 * https://api.jcdecaux.com/vls/v1/contracts?apiKey=1409f7759c6065f255490f32441e1cc5bc8922da
 */

public class JCDecaux {

    /* JCDecaux API KEY */
    public static final String API_KEY = "1409f7759c6065f255490f32441e1cc5bc8922da";

    /* Intents Extra Keys */
    public static final String KEY_CONTRACT = "JCD_CONTRACT_SELECTED";
    public static final String KEY_CONTRACTS = "JCD_CONTRACTS_LIST";

    public static final String KEY_STATION = "JCD_STATION_SELECTED";
    public static final String KEY_STATIONS = "JCD_STATIONS_LIST";

    /* Activities Requests Codes */
    public static final int RQC_CONTRACTS = 123;
    public static final int RQC_STATIONS_L = 456;
    public static final int RQC_STATIONS_M = 789;

    /* Activities Result Codes */
    public static final int RESULT_CLOSE = 3;
    public static final int RESULT_ASK_LIST = 4;
    public static final int RESULT_ASK_MAP = 5;

    /* JCDecaux class : Contracts container */
    private ArrayList<Contract> contracts;
    public JCDecaux() { contracts = new ArrayList<>(); }
    public ArrayList<Contract> getContracts() { return contracts; }
    public void setContracts(ArrayList<Contract> contracts) { this.contracts = contracts; }

    //TODO : Add an update datetime ?

}
