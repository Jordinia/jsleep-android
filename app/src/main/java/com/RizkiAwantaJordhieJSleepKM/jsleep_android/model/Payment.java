package com.RizkiAwantaJordhieJSleepKM.jsleep_android.model;

import java.util.Date;

public class Payment extends Invoice {
    public Date to;
    public Date from;
    private int roomId;
    public Payment(int id) {
        super(id);
    }
}
