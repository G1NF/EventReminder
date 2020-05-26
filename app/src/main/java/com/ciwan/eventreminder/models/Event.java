package com.ciwan.eventreminder.models;

public class Event {
    private int id;
    private String name;
    private String detail;
    private String startDate;
    private String endDate;
    private String eventType;
    private String address;
    private String repeatFreq;


    public Event(int id, String name, String detail, String startDate, String endDate, String eventType, String address, String repeatFreq) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventType = eventType;
        this.address = address;
        this.repeatFreq = repeatFreq;
    }

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRepeatFreq() {
        return repeatFreq;
    }

    public void setRepeatFreq(String repeatFreq) {
        this.repeatFreq = repeatFreq;
    }
}
