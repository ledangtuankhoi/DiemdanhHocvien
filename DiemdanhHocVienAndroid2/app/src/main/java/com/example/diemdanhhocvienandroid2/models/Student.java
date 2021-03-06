package com.example.diemdanhhocvienandroid2.models;

import java.io.Serializable;

public class Student  implements Serializable {
    private int id;
    private String lastName;
    private String firstName;
    private String holyName;
    private String BOD;
    private String numPhone;
    private String email;
    private int parentId;
    private int classId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHolyName() {
        return holyName;
    }

    public void setHolyName(String holyName) {
        this.holyName = holyName;
    }

    public String getBOD() {
        return BOD;
    }

    public void setBOD(String BOD) {
        this.BOD = BOD;
    }

    public String getNumPhone() {
        return numPhone;
    }

    public void setNumPhone(String numPhone) {
        this.numPhone = numPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", holyName='" + holyName + '\'' +
                ", BOD='" + BOD + '\'' +
                ", numPhone='" + numPhone + '\'' +
                ", email='" + email + '\'' +
                ", parentId=" + parentId +
                ", classId=" + classId +
                '}';
    }
}
