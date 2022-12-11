package com.RizkiAwantaJordhieJSleepKM.jsleep_android.model;

public class Invoice extends Serializable {
    public int buyerId;
    public int renterId;
    public enum RoomRating{
        NONE,
        BAD,
        NEUTRAL,
        GOOD
    }
    public enum PaymentStatus{
        FAILED,
        WAITING,
        SUCCESS
    }
    public Invoice(int id) {
        super(id);
    }
    public RoomRating rating;
    public PaymentStatus status;


}
