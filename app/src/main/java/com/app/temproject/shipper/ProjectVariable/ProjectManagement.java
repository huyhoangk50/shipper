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

    public static void changeURL(String baseURL){
        Constant.URL_SP_LOAD_REQUEST = baseURL + "api/accounts/login";
        Constant.URL_SP_LOAD_REGISTERS = baseURL + "api/accounts/register";
        Constant.URL_SP_LOAD_DETAIL_REQUEST = baseURL + "";
        Constant.URL_LOGIN = baseURL + "api/accounts/login";
        Constant.URL_ACTIVE_ACCOUNT = baseURL + "api/accounts/active";
        Constant.URL_CONFIRM_EMAIL = baseURL + "api/accounts/requireResetPassword";
        Constant.URL_RESET_PASSWORD = baseURL + "api/accounts/checkResetCodeAndUpdatePassword";
        Constant.URL_ST_CREATE_REQUEST = baseURL + "api/requests";
        Constant.URL_ST_LOAD_REQUEST = baseURL + "";
    }
}
