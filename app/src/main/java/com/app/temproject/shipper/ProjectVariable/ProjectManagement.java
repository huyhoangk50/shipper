package com.app.temproject.shipper.ProjectVariable;

import com.app.temproject.shipper.Object.Shipper;
import com.app.temproject.shipper.Object.Store;

/**
 * Created by huyhoang on 12/08/2016.
 */
public class ProjectManagement {

    public static final String SERVER_NAME = "http://www.";
    public static Shipper shipper;
    public static Store store;

    //constant for url
    public static String baseUrl = "";
    public static String urlSpLoadNewRequest = "";
    public static String urlSpRegister ="";
    public static String urlSpLoadDetailRequest = "";
    public static String urlLogin = "";
    public static String urlActiveAccount = "";
    public static String urlConfirmEmail = "";
    public static String urlResetPassword = "";
    public static String urlStCreateRequest = "";
    public static String urlStLoadRequest = "";
    public static String urlSpLoadWaitingRequest = "";
    public static String urlSpLoadProcessingRequest = "";
    public static String urlSpLoadCompletedRequest = "";
    public static void changeURL(String baseURL){
        urlSpLoadNewRequest = baseURL + "api/requests/new/";
        urlSpLoadProcessingRequest = baseURL + "api/requests/processing/";
        urlSpLoadWaitingRequest = baseURL + "api/requests/waiting/";
        urlSpLoadCompletedRequest = baseURL + "api/requests/completed/";
        urlSpRegister = baseURL + "api/accounts/register";
        urlSpLoadDetailRequest = baseURL + "";
        urlLogin = baseURL + "api/accounts/login";
        urlActiveAccount = baseURL + "api/accounts/active";
        urlConfirmEmail = baseURL + "api/accounts/requireResetPassword";
        urlResetPassword = baseURL + "api/accounts/checkResetCodeAndUpdatePassword";
        urlStCreateRequest = baseURL + "api/requests";
        urlStLoadRequest = baseURL + "";

    }
}
