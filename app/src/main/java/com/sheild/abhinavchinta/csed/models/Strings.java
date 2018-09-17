package com.sheild.abhinavchinta.csed.models;

/**
 * Created by Abhinav Chinta on 2/3/2018.
 */

public class Strings {

    public static final String STORAGE_PATH_UPLOADS = "uploads/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";

    public Strings(){}

    public static String getDepartmentName(String dept){
        switch (dept)
        {
            case ("0"):
                return ("Editorial and Blog");

            case ("1"):
                return ("Events, UR and Strategies");

            case ("2"):
                return ("Expansion");

            case ("3"):
                return ("General Secretary");

            case ("4"):
                return ("Human Resources");

            case ("5"):
                return ("Marketing");

            case ("6"):
                return ("Public Relations");

            case ("7"):
                return ("Startups");
            case ("8"):
                return ("Technical and Design");
            default:
                return (" ");

        }


    }

}
