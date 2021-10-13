package com.example.crimereportimg;

import java.util.Date;
import java.util.UUID;

public class Crime
{
    private String titleCrime;
    private UUID mid;
    private Date crimDate;
    private boolean status;


    public Crime()
    {
       this(UUID.randomUUID());
    }

    public Crime(UUID UUImid)
    {
        this.mid=UUImid;
        crimDate=new Date();

    }

    public void setTitleCrime(String titleCrime) {
        this.titleCrime = titleCrime;
    }

    public void setMid(UUID mid) {
        this.mid = mid;
    }

    public void setCrimDate(Date crimDate) {
        this.crimDate = crimDate;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//Setters

    public String getTitleCrime() {
        return titleCrime;
    }

    public UUID getMid() {
        return mid;
    }

    public Date getCrimDate() {
        return crimDate;
    }

    public boolean isStatus() {
        return status;
    }
}
