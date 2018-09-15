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

    public Test() {
    }

    public static String getDate() {
        return date;
    }

    public static String getDepartmentcode() {
        return departmentcode;
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
