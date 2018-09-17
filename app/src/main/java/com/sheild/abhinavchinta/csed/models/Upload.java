package com.sheild.abhinavchinta.csed.models;

/**
 * Created by Abhinav on 15-09-2018.
 */

public class Upload {

    public String name;
    public String author;
    public String date;
    public String url;
    public int type;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String author, String date, String url, int type) {
        this.name = name;
        this.author = author;
        this.date = date;
        this.url = url;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
