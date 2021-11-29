package com.example.diemdanhhocvienandroid2.models;

public class ClassP {
    private int id ;
    private String className ;
    private String codeName ;
    private String startDate ;
    private String endDate ;
    private String dayOfWeek ;
    private int userId ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

     public String getStartDate() {
        return  startDate;
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

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
