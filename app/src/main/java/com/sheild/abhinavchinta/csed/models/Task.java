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

    public Task(){

    }

    public Task(String date, String deadline, String departmentCode, String designation, String message, String name, String registrationNumber, int  status, String time) {
        this.date = date;
        this.deadline = deadline;
        this.departmentCode = departmentCode;
        this.designation = designation;
        this.message = message;
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.status = status;
        this.time = time;
    }

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
