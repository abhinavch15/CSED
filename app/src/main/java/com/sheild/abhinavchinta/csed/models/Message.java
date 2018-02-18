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

    public Message(String date, String deptcode, String designation, String message, String name, String regno, String time) {
        this.date = date;
        this.deptcode = deptcode;
        this.designation = designation;
        this.message = message;
        this.name = name;
        this.regno = regno;
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

