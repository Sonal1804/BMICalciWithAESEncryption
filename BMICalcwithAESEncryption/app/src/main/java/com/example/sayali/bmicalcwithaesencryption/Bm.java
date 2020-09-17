package com.example.sayali.bmicalcwithaesencryption;

public class Bm {

    private String date,time,bmi;

    public Bm() {
    }

    public Bm(String date, String time, String bmi) {
        this.date = date;
        this.time = time;
        this.bmi=bmi;
    }

    @Override
    public String toString() {
        return "Date =" + date + ", Time = " + time + ", BMI = "+ bmi;

    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }




}
