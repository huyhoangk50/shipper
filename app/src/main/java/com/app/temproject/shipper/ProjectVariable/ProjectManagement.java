package com.app.temproject.shipper.ProjectVariable;

import com.app.temproject.shipper.Object.Shipper;
import com.app.temproject.shipper.Object.SocketConnection;
import com.app.temproject.shipper.Object.Store;

/**
 * Created by huyhoang on 12/08/2016.
 */
public class ProjectManagement {

    public static Shipper shipper;
    public static Store store;
    public static SocketConnection socketConnection;

    //constant for url
    public static String baseUrl = "";
    public static String urlLogin = "";
    public static String urlActiveAccount = "";
    public static String urlConfirmEmail = "";
    public static String urlResetPassword = "";

    public static String urlSpLoadNewRequest = "";
    public static String urlSpRegister ="";
    public static String urlSpLoadDetailRequest = "";
    public static String urlSpLoadWaitingRequest = "";
    public static String urlSpLoadProcessingRequest = "";
    public static String urlSpLoadCompletedRequest = "";
    public static String urlSpFinishRequest = "";
    public static String urlSpApplyRequest = "";
    public static String urlSpCancelRequest = "";

    public static String urlStCreateRequest = "";
    public static String urlStLoadRequest = "";
    public static String urlStLoadWaitingRequest = "";
    public static String urlStLoadCompletedRequest = "";
    public static String urlStLoadNewRequest = "";
    public static String urlStLoadProcessingRequest = "";
    public static String urlStCancelRequest = "";
    public static String urlStLoadDetailRequest = "";
    public static String urlStAcceptShipper = "";
    public static String urlStConfirmCompletedRequest = "";
    public static String urlLoadNotifications = "";


    public static void changeURL(String baseURL){
        urlLogin = baseURL + "api/accounts/login";
        urlActiveAccount = baseURL + "api/accounts/active";
        urlConfirmEmail = baseURL + "api/accounts/requireResetPassword";
        urlResetPassword = baseURL + "api/accounts/checkResetCodeAndUpdatePassword";
        urlLoadNotifications = baseURL + "api//notifications/users/";//get

        urlSpLoadNewRequest = baseURL + "api/requests/shipper/new/";
        urlSpLoadProcessingRequest = baseURL + "api/requests/shipper/processing/";
        urlSpLoadWaitingRequest = baseURL + "api/requests/shipper/waiting/";
        urlSpLoadCompletedRequest = baseURL + "api/requests/shipper/completed/";
        urlSpRegister = baseURL + "api/accounts/register";
        urlSpLoadDetailRequest = baseURL + "api/requests/request/";
        urlSpFinishRequest = baseURL + "api/requests/confirm/";
        urlSpCancelRequest = baseURL + "api/responses/cancel";

        urlStCreateRequest = baseURL + "api/requests/create";
//        urlStLoadRequest = baseURL + "";
        urlSpApplyRequest = baseURL + "api/responses/create";
        urlStLoadWaitingRequest = baseURL + "api/requests/store/waiting/";
        urlStLoadCompletedRequest = baseURL + "api/requests/store/completed/";
        urlStLoadProcessingRequest = baseURL + "api/requests/store/processing/";
        urlStLoadDetailRequest = baseURL + "api/requests/store/specific-item/";
        urlStCancelRequest = baseURL + "api/requests/cancel/";
        urlStAcceptShipper = baseURL + "api/responses/accept";//post
        urlStConfirmCompletedRequest = baseURL + "api/requests/confirmed/";//put
    }
}
