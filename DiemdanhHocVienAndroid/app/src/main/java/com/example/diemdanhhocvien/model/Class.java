package com.example.diemdanhhocvien.model;

import java.time.LocalDate;

public class Class {
    private int id ;
    private String className ;
    private String codeName ;
    private LocalDate startDate ;

    public Class() {
    }

    public Class(int id, String className, String codeName, LocalDate startDate, LocalDate endDate, String dayOfWeek, int userId) {
        this.id = id;
        this.className = className;
        this.codeName = codeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dayOfWeek = dayOfWeek;
        this.userId = userId;
    }

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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

    private LocalDate endDate ;
    private String dayOfWeek ;
    private int userId ;
}
