package com.iut.tbg.jcdecaux.Models;

import java.io.Serializable;

public class City implements Serializable {

    //region Model.City : Attributes

    private int id;
    private String name;
    private String country_code;

    //endregion

    public City(int id, String name, String country_code) {

        this.id = id;
        this.name = name;
        this.country_code = country_code;

    }

    //region Model.City : Accessors

    public int getID() { return id; }
    public String getName() { return name; }
    public String getCountryCode() { return country_code; }

    //endregion

    //region Model.City : Mutators

    public void setID(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCountryCode(String country_code) { this.country_code = country_code; }

    //endregion

}
