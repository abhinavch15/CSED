package com.sheild.abhinavchinta.csed.models;


public class Message {
    String date;
    String deptcode;
    String designation;
    String message;
    String name;
    String regno;;
    String time;

    public Message(){}

    public Message(String message, String name, String date) {
        this.date = date;
        this.message = message;
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public String getDesignation() {
        return designation;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getRegno() {
        return regno;
    }

    public String getTime() {
        return time;
    }
}

