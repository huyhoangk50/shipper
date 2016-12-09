package com.app.temproject.shipper.ProjectVariable;

/**
 * Created by Admin on 09/11/2016.
 */

public class Constant {
    public static final int STORE_ROLE = 2;
    public static final int SHIPPER_ROLE = 1;

    public static final String GPS = "gps";

    public static final String VIET_NAM = "Việt Nam";
    public static final String HA_NOI = "Hà Nội";
    public static final String HO_CHI_MINH = "TP Hồ Chí Minh";


    //key in http request

    public static final String KEY_EMAIL = "email";
    public static final String KEY_ID = "id";
    public static final String KEY_ROLE = "role";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NAME = "name";
    public static final String KEY_STORE_TYPE = "store_type";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_STREET = "street";
    public static final String KEY_CITY = "city";
    public static final String KEY_DISTRICT = "district";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_COUNTRY = "country";

    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_DEPOSIT = "deposit";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_STORE_ID = "store_id";
    public static final String KEY_DESTINATION = "destination";
    public static final String KEY_PRICE = "price";
    public static final String KEY_PRODUCT_ID = "product_id";
    public static final String KEY_PRODUCT_NAME = "product_name";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CREATED_TIME = "created_time";
    public static final String KEY_UPDATED_TIME = "updated_time";
    public static final String KEY_STORE_NAME = "store_name";
    public static final String KEY_STORE_POSITION = "store_position";
    public static final String KEY_REQUEST_ID = "requestId";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_CUSTOMER_NAME = "customer_name";
    public static final String KEY_RATING = "rating";
    public static final String KEY_VOTE = "vote";
    public static final String KEY_ERROR = "err";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DATA = "data";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_ID_ACCOUNT = "account_id";
    public static final String KEY_ACTIVE_CODE = "active_code";
    public static final String KEY_RESET_CODE = "reset_code";

    //constant for url
    public static final String BASE_URL = "http://192.168.0.104:3000/";
    public static final String URL_SP_LOAD_REQUEST = BASE_URL + "api/accounts/login";
    public static final String URL_SP_LOAD_REGISTERS = BASE_URL + "api/accounts/register";
    public static final String URL_SP_LOAD_DETAIL_REQUEST = BASE_URL + "";
    public static final String URL_LOGIN = BASE_URL + "api/accounts/login";
    public static final String URL_ACTIVE_ACCOUNT = BASE_URL + "api/accounts/active";
    public static final String URL_CONFIRM_EMAIL = BASE_URL + "api/accounts/requireResetPassword";
    public static final String URL_RESET_PASSWORD = BASE_URL + "api/accounts/checkResetCodeAndUpdatePassword";
    public static final String URL_ST_CREATE_REQUEST = BASE_URL + "api/requests";
    public static final String URL_ST_LOAD_REQUEST = BASE_URL + "";

    public static final String FROM = "Từ ";
    public static final String TO = " đến ";
    public static final String CUSTOMER = "Khách hàng: ";

    public static final int ALL = 0;
    public static final int PENDING_STATUS = 1;
    public static final int ON_GOING_STATUS = 2;
    public static final int COMPLETED_STATUS = 3;

    public static final String POST_METHOD = "POST";
    public static final String GET_METHOD = "GET";
    public static final String PUT_METHOD = "PUT";
    public static final String DELETE_METHOD = "DELETE";

    public static final String JSON_TYPE = "application/json; charset=utf-8";
    public static final String CONTENT_TYPE = "Content-Type";
    //ERROR
    public static final String INCORRECT_REGISTRATION_INFORMATION = "Thông tin đăng ký không hợp lệ";
    public static final String INCORRECT_INFORMATION = "Tài khoản hoặc mật khẩu không đúng";
    public static final String NO_DATA_IS_RECEIVED = "Không có dữ liệu";
    public static final String INVALID_PHONE_NUMBER = "Số điện thoại không hợp lệ";
    public static final String INVALID_EMAIL = "Email không hợp lệ";
    public static final String INVALID_PASSWORD = "Mật khẩu ít hơn 6 ký tự";
    public static final String INCORRECT_PASSWORD = "Không trùng với mật khẩu";
    public static final String INVALID_STORE = "Điền tên cửa hàng";

    public static final String DUPLICATE_EMAIL = "Email này đã tồn tại";

    public static final String NOT_EXISTED_EMAIL = "Email không tồn tại";
    public static int ACTIVE_STATUS = 1;
    public static int NOT_ACTIVE_STATUS = 0;
    public static int MIN_PASS_LENGTH = 6;
    public static int MIN_PHONE_NUM_LENGTH = 9;
    public static int MAX_PHONE_NUM_LENGTH = 12;
}
