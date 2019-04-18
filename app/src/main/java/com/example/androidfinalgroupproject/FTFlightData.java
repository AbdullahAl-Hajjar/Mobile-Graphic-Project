package com.example.androidfinalgroupproject;

/**
 * This class houses getters and setters
 */


public class FTFlightData {

    private double id;
    private double fltSpeed;
    private String fltNbr;
    private String fltStatus;
    private String fltLong;
    private String fltLat;
    private String fltDep;
    private String fltArr;
    private String airline;

    /**
     * Default constructor
     */
    public FTFlightData(){


    }
    public void setId(double id){
        this.id = id;
    }

    public double getId(){
        return id;
    }

    public void setFltSpeed(double fltSpeed){
        this.fltSpeed = fltSpeed;
    }

    public double getFltSpeed(){
        return fltSpeed;
    }

    public void setFltNbr(String fltNbr){
        this.fltNbr = fltNbr;
    }

    public String getFltNbr(){
        return fltNbr;
    }

    public void setFltStatus(String fltStatus){
        this.fltStatus = fltStatus;
    }

    public String getFltStatus(){
        return fltStatus;
    }

    public void setFltLong(String fltLong){
        this.fltLong = fltLong;
    }
    public String getFltLong() {
        return fltLong;
    }

    public void setFltLat(String fltLat){
        this.fltLat = fltLat;
    }

    public String getFltLat(){
        return fltLat;
    }

    public void setFltDep(String fltDep){
        this.fltDep = fltDep;
    }

    public String getFltDep(){
        return fltDep;
    }

    public void setFltArr(String fltArr) {
        this.fltArr = fltArr;
    }

    public String getFltArr(){
        return fltArr;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getAirline(){
        return airline;
    }

}
