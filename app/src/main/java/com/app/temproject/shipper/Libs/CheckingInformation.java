package com.app.temproject.shipper.Libs;

import com.app.temproject.shipper.ProjectVariable.Constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by paladin on 08/12/2016.
 */

public class CheckingInformation {
    public static boolean isValidEmail(String email){
        String regexEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Matcher matcherEmail = Pattern.compile(regexEmail).matcher(email.trim());

        if(matcherEmail.find()){
            return true;
        }
        return  false;
    }

    public static boolean isValidPassword(String password){
        if(password.length() < 6){
            return  false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(String phoneNumber){
        String regexPhoneNumber = "^[0-9]*$";
        Matcher matcherPhoneNumber = Pattern.compile(regexPhoneNumber).matcher(phoneNumber.trim());

        if(!phoneNumber.isEmpty() && matcherPhoneNumber.find() && (phoneNumber.trim().length() > Constant.MIN_PHONE_NUM_LENGTH) && (phoneNumber.trim().length() < Constant.MAX_PHONE_NUM_LENGTH)){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String value){
        if(value.trim().equals("")){
            return true;
        }
        return false;
    }

    public static boolean isNumericGreaterThanZero(String number){
        String regexNumber = "^[0-9]*$";
        Matcher matcherNumber = Pattern.compile(regexNumber).matcher(number);
        if(matcherNumber.find() && !number.isEmpty()){
            if( Integer.parseInt(number) > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidDateTime(String startedDateTime, String endedDateTime){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date startDateTime;
        Date endDateTime;
        Date systemDateTime;
        boolean isTrue = false;
        try {
            startDateTime = dateFormat.parse(startedDateTime);
            endDateTime = dateFormat.parse(endedDateTime);
            String sysDateTime = dateFormat.format(new Date());
            systemDateTime = dateFormat.parse(sysDateTime);
            isTrue =  (startDateTime.compareTo(endDateTime) < 0) & (systemDateTime.compareTo(startDateTime) < 0);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return isTrue;
    }
}
