package com.app.temproject.shipper.Libs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by paladin on 14/01/2017.
 */

public class TimeProcessing {
    public static String convertTime(String time){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat dateF = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        dateF.setTimeZone(TimeZone.getTimeZone("GMT+2"));

        String convertedTime ="";

        try {
            convertedTime = dateF.format(dateFormat.parse(time)).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  convertedTime;
    }

    public static String convertTimeToDate(String time){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat dateF = new SimpleDateFormat("HH:mm:ss");
        dateF.setTimeZone(TimeZone.getTimeZone("GMT+2"));

        String convertedTime ="";

        try {
            convertedTime = dateF.format(dateFormat.parse(time)).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  convertedTime;
    }
}
