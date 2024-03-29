package com.sheild.abhinavchinta.csed.models;


public class Task {
    private String date;
    private String deadline;
    private String departmentCode;
    private String designation;
    private String message;
    private String name;
    private String registrationNumber;
    private int status;
    private String time;
    int day;
    int month;
    int year;

    public Task(){

    }

    public Task( String departmentCode, String message,String date,String deadline, int day, int month, int year, String name) {
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
        this.deadline = deadline;
        this.departmentCode = departmentCode;
        this.designation = designation;
        this.message = message;
        this.name = name;
    }

    public int getDay() {return day;}

    public void setDay(int day) {this.day = day;}

    public int getMonth() {return month;}

    public void setMonth(int month) {this.month = month;}

    public int getYear() {return year;}

    public void setYear(int year) {this.year = year;}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
