package com.sheild.abhinavchinta.csed.models;



public class Member {
    int available;
    String department;
    String designation;
    String email;
    int isAdmin;
    String name;
    long phoneNumber;
    String registrationNumber;

    public Member(){}

    public Member(int available, String department, String designation, String email, int isadmin, String name, long phoneno, String regno) {
        this.available = available;
        this.department = department;
        this.designation = designation;
        this.email = email;
        this.isAdmin = isadmin;
        this.name = name;
        this.phoneNumber = phoneno;
        this.registrationNumber = regno;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isadmin) {
        this.isAdmin = isadmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneno) {
        this.phoneNumber = phoneno;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String regno) {
        this.registrationNumber = regno;
    }
}
