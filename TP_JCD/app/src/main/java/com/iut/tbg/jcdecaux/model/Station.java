package com.iut.tbg.jcdecaux.model;

import java.sql.Time;
import java.sql.Timestamp;

public class Station {

    private enum Status {
        OPEN,
        CLOSE,
        UNKNOWN,
    }

    //region Model.Station : Attributes
    private int number;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private boolean banking;
    private boolean bonus;
    private Status status;
    private int bike_stands;
    private int available_bike_stands;
    private int available_bike;
    private Timestamp last_update;
    //endregion

    // Constructor : Static data only
    public Station(int number, String name, String address, double latitude, double longitude, boolean banking, boolean bonus)  {

        // Static data
        this.number = number;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.banking = banking;
        this.bonus = bonus;

        // Dynamic Data : Default values
        this.refresh(Status.UNKNOWN, -1, -1, -1, 0);

    }

    // Constructor : Static and Dynamic data
    public Station(int number, String name, String address, double latitude, double longitude, boolean banking, boolean bonus, String status, int bike_stands, int available_bike_stands, int available_bike, long last_update)  {

        // Static data
        this.number = number;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.banking = banking;
        this.bonus = bonus;

        // Dynamic Data : Initialization
        this.refresh(status, bike_stands, available_bike_stands, available_bike, last_update);

    }

    //region Model.Station : Accessors

    // Static data
    public int getNumber() { return number; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public boolean isBanking() { return banking; }
    public boolean isBonus() { return bonus; }

    // Dynamic data
    public Status getStatus() { return status; }
    public int getBike_stands() { return bike_stands; }
    public int getAvailable_bike_stands() { return available_bike_stands; }
    public int getAvailable_bike() { return available_bike; }
    public Timestamp getLast_update() { return last_update; }

    public String getStatusToString() {

        switch(this.status) {

            case OPEN:
                return "OPEN";

            case CLOSE:
                return "CLOSE";

            case UNKNOWN:
                return "UNKNOW";

            default:
                return "UNKNOW";

        }

    }
    //endregion

    //region Model.Station : Mutators

    // Static data
    public void setNumber(int number) { this.number = number; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public void setBanking(boolean banking) { this.banking = banking; }

    // Dynamic data
    public void setBonus(boolean bonus) { this.bonus = bonus; }
    public void setStatus(Status status) { this.status = status; }
    public void setBike_stands(int bike_stands) { this.bike_stands = bike_stands; }
    public void setAvailable_bike_stands(int available_bike_stands) { this.available_bike_stands = available_bike_stands; }
    public void setAvailable_bike(int available_bike) { this.available_bike = available_bike; }
    public void setLast_update(Timestamp last_update) { this.last_update = last_update; }

    public void setStatusFromString(String status) {

        switch(status) {

            case "OPEN":
                this.status = Status.OPEN;
                break;

            case "CLOSE":
                this.status = Status.CLOSE;
                break;

            default:
                this.status = Status.UNKNOWN;
                break;

        }

    }
    //endregion

    // Refresh Dynamic data (with status as a String)
    public void refresh(String status, int bike_stands, int available_bike_stands, int available_bike, long last_update) {

        switch(status) {

            case "OPEN":
                this.status = Status.OPEN;
                break;

            case "CLOSE":
                this.status = Status.CLOSE;
                break;

            default:
                this.status = Status.UNKNOWN;
                break;

        }

        this.bike_stands = bike_stands;
        this.available_bike_stands = available_bike_stands;
        this.available_bike = available_bike;
        this.last_update = new Timestamp(last_update);

    }

    // Refresh Dynamic data (with status as a Status (Enum))
    public void refresh(Status status, int bike_stands, int available_bike_stands, int available_bike, long last_update) {

        this.status = status;
        this.bike_stands = bike_stands;
        this.available_bike_stands = available_bike_stands;
        this.available_bike = available_bike;
        this.last_update = new Timestamp(last_update);
    }

}

