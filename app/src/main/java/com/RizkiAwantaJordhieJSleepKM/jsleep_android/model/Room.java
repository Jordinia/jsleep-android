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
    public Facility facility;

}
