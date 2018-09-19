package com.sheild.abhinavchinta.csed.models;

import java.util.ArrayList;
import java.util.List;



public class Test {
    public static List<Message> listdata= new ArrayList<>();
    public static List<Member> listmembers= new ArrayList<>();
    public static List<Task> listtasks= new ArrayList<>();
    public static String name;
    public static String department;
    public static String date;
    public static int IsAdmin;
    public static String departmentcode;
    public static String departmentcode2 = "";
    public static String departmentcode3 = "";
    public static String department2;
    public static String department3;

    public Test() {
    }

    public static String getDate() {
        return date;
    }

    public static String getDepartmentcode() {
        return departmentcode;
    }

    public static void setListdata(List<Message> listdata) {
        Test.listdata = listdata;
    }

    public static void setListmembers(List<Member> listmembers) {
        Test.listmembers = listmembers;
    }

    public static void setListtasks(List<Task> listtasks) {
        Test.listtasks = listtasks;
    }

    public static void setName(String name) {
        Test.name = name;
    }

    public static void setDepartment(String department) {
        Test.department = department;
    }

    public static void setDate(String date) {
        Test.date = date;
    }

    public static int getIsAdmin() {
        return IsAdmin;
    }

    public static void setIsAdmin(int isAdmin) {
        IsAdmin = isAdmin;
    }

    public static void setDepartmentcode(String departmentcode) {
        Test.departmentcode = departmentcode;
    }

    public static String getDepartmentcode2() {
        return departmentcode2;
    }

    public static void setDepartmentcode2(String departmentcode2) {
        Test.departmentcode2 = departmentcode2;
    }

    public static String getDepartmentcode3() {
        return departmentcode3;
    }

    public static void setDepartmentcode3(String departmentcode3) {
        Test.departmentcode3 = departmentcode3;
    }

    public static String getDepartment2() {
        return department2;
    }

    public static void setDepartment2(String department2) {
        Test.department2 = department2;
    }

    public static String getDepartment3() {
        return department3;
    }

    public static void setDepartment3(String department3) {
        Test.department3 = department3;
    }

    public static List<Message> getListdata() {
        return listdata;
    }

    public static List<Member> getListmembers() {
        return listmembers;
    }

    public static List<Task> getListtasks() { return listtasks; }

    public static String getName() {
        return name;
    }

    public static String getDepartment() {
        return department;
    }
}
