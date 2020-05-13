package com.example.androidmedicode;

import java.util.Date;

public class MedicalEvents {

    private String EventID, Date, ShortDescription, LongDescription, Doctor_GMC;
    private int Thumbnail;


    public MedicalEvents() {

    }

    //    constructor
    public MedicalEvents(String eventID, String date, String shortDescription, String longDescription, String doctor_GMC, int thumbnail) {
        EventID = eventID;
        Date = date;
        ShortDescription = shortDescription;
        LongDescription = longDescription;
        Doctor_GMC = doctor_GMC;
        Thumbnail = thumbnail;

    }

    //    getter
    public String getEventID() {
        return EventID;
    }

    public String getDate() {
        return Date;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public String getLongDescription() {
        return LongDescription;
    }

    public String getDoctor_GMC() {
        return Doctor_GMC;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    //    setter
    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        LongDescription = longDescription;
    }

    public void setDoctor_GMC(String doctor_GMC) {
        Doctor_GMC = doctor_GMC;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
