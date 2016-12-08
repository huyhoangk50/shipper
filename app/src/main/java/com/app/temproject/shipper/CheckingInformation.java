package com.app.temproject.shipper;

import com.app.temproject.shipper.ProjectVariable.Constant;

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

        if(matcherPhoneNumber.find() && (phoneNumber.trim().length() > Constant.MIN_PHONE_NUM_LENGTH) && (phoneNumber.trim().length() < Constant.MAX_PHONE_NUM_LENGTH)){
            return true;
        }
        return false;
    }
}
