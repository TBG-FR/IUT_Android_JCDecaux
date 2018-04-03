package com.iut.tbg.jcdecaux.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Contract implements Serializable {

    //region Model.Contract : Attributes

    private String name;
    private String commercial_name;
    private String country_code;
    private ArrayList<City> cities;
    private ArrayList<Station> stations;

    //endregion

    //region Model.Contract : Constructors

    public Contract(String name, String commercial_name, String country_code, ArrayList<String> cities, ArrayList<Station> stations) {

        // Basic Information
        this.name = name;
        this.commercial_name = commercial_name;
        this.country_code = country_code;

        // Cities
        this.cities = new ArrayList<>();
        int id = 0;
        for (String city : cities) {
            this.cities.add(new City(id, city, this.country_code));
            id++;
        }

        // Stations
        this.stations = new ArrayList<>(stations);

    }

    public Contract(String name, String commercial_name, String country_code, String[] cities, ArrayList<Station> stations) {

        // Basic Information
        this.name = name;
        this.commercial_name = commercial_name;
        this.country_code = country_code;

        // Cities
        this.cities = new ArrayList<>();
        int id = 0;
        for (String city : cities) {
            this.cities.add(new City(id, city, this.country_code));
            id++;
        }

        // Stations
        this.stations = new ArrayList<>(stations);

    }

    public Contract(String name, String commercial_name, String country_code, ArrayList<String> cities) {

        // Basic Information
        this.name = name;
        this.commercial_name = commercial_name;
        this.country_code = country_code;

        // Cities
        this.cities = new ArrayList<>();
        int id = 0;
        for (String city : cities) {
            this.cities.add(new City(id, city, this.country_code));
            id++;
        }

        // Stations
        this.stations = new ArrayList<>();

    }

    public Contract(String name, String commercial_name, String country_code, String[] cities) {

        // Basic Information
        this.name = name;
        this.commercial_name = commercial_name;
        this.country_code = country_code;

        // Cities
        this.cities = new ArrayList<>();
        int id = 0;
        for (String city : cities) {
            this.cities.add(new City(id, city, this.country_code));
            id++;
        }

        // Stations
        this.stations = new ArrayList<>();

    }

    //endregion

    //region Model.Contract : Accessors

    public String getName() { return name; }
    public String getCommercialName() { return commercial_name; }
    public String getCountryCode() { return country_code; }
    public ArrayList<City> getCities() { return cities; }
    public ArrayList<Station> getStations() { return stations; }

    public String getCitiesToString() {

        String cities = "";
        for(City city : this.cities) { cities += city.getName() + ", "; }
        return cities.substring(0,cities.length()-2);

    }

    //endregion

    //region Model.Contract : Mutators

    public void setName(String name) { this.name = name; }
    public void setCommercialName(String commercial_name) { this.commercial_name = commercial_name; }
    public void setCountryCode(String country_code) { this.country_code = country_code; }
    public void setCities(ArrayList<City> cities) { this.cities = cities; }
    public void setStations(ArrayList<Station> stations) { this.stations = stations; }

    //endregion

}