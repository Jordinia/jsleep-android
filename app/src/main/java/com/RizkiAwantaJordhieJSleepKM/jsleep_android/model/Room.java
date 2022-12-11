package com.RizkiAwantaJordhieJSleepKM.jsleep_android.model;

import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable {
    public int accountId;
    public int size;
    public String name;
    public ArrayList<Date> booked;
    public String address;
    public Price price;
    public City city;
    public BedType bedType;
    public ArrayList<Facility> facility = new ArrayList<>();

    public Room(int id, int accountId, String name, int size, Price price,
                ArrayList<Facility> facility, City city, String address, BedType bedType) {
        super(id);
        this.accountId = accountId;
        this.city = city;
        this.address = address;
        this.name = name;
        this.size = size;
        this.price = price;
        this.facility.addAll(facility);
        this.booked = new ArrayList<Date>();
        this.bedType = bedType.SINGLE;
    }



}
