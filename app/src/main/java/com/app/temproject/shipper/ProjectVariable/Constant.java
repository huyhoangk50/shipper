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
    public static final String KEY_TYPE = "type";
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
    public static final String KEY_ACTOR_NAME = "actor_name";
    public static final String KEY_CREATED_TIME = "created_time";
    public static final String KEY_UPDATED_TIME = "updated_time";
    public static final String KEY_STORE_NAME = "name";
    public static final String KEY_CODE = "code";
    public static final String KEY_STORE_POSITION = "street";
    public static final String KEY_REQUEST_ID = "request_id";
    public static final String KEY_USER_ID = "user_id";
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
    public static final String KEY_IMAGE_BASE_64_STRING = "image_base64string";
    public static final String KEY_IMAGE_EXTENSION = "image_extension";
    public static final String KEY_SHIPPER_ID = "shipper_id";
    public static final String KEY_STORE = "store";
    public static final String KEY_REQUEST = "request";
    public static final String KEY_RESPONSE = "response";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_RESPONSE_STATUS = "response_status";
    public static final String KEY_RESPONSE_ID = "response_id";


    public static final String FROM = "Từ ";
    public static final String TO = " đến ";
    public static final String CUSTOMER = "Khách hàng: ";
    public static final String STORE = "Cửa hàng: ";
    public static final String PRICE = "Giá ship: ";
    public static final String THOUSAND_DONG = "Nghìn đồng";

    public static final int NEW_REQUEST = 0;
    public static final int WAITING_REQUEST = 1;
    public static final int PROCESSING_REQUEST = 2;
    public static final int DONE_REQUEST = 3;
    public static final int COMPLETED_REQUEST = 4;
    public static final int CANCELED_REQUEST = 5;



    public static final int NEW_RESPONSE = -1;
    public static final int WAITING_RESPONSE = 0;
    public static final int BE_CANCELED_RESPONSE = 1;
    public static final int ACCEPTED_RESPONSE = 2;
    public static final int CANCELED_RESPONSE = 3;

    //http protocol
    public static final String POST_METHOD = "POST";
    public static final String GET_METHOD = "GET";
    public static final String PUT_METHOD = "PUT";
    public static final String DELETE_METHOD = "DELETE";

    //http configuration
    public static final String JSON_TYPE = "application/json; charset=utf-8";
    public static final String CONTENT_TYPE = "Content-Type";

    //ERROR
    public static final String INCORRECT_REGISTRATION_INFORMATION = "Thông tin đăng ký không hợp lệ";
    public static final String INCORRECT_CREATE_REQUEST_INFORMATION = "Thông tin đơn hàng không hợp lệ";
    public static final String INCORRECT_INFORMATION = "Tài khoản hoặc mật khẩu không đúng";
    public static final String NO_DATA_IS_RECEIVED = "Không có dữ liệu";
    public static final String INVALID_PHONE_NUMBER = "Số điện thoại không hợp lệ";
    public static final String INVALID_EMAIL = "Email không hợp lệ";
    public static final String INVALID_PASSWORD = "Mật khẩu ít hơn 6 ký tự";
    public static final String INCORRECT_PASSWORD = "Không trùng với mật khẩu";
    public static final String INVALID_STORE = "Điền tên cửa hàng";
    public static final String INVALID_PRODUCT_NAME = "Điền tên sản phẩm";
    public static final String INVALID_CUSTOMER_NAME = "Điền tên khách hàng";
    public static final String INVALID_DEPOSIT = "Tiền đặt cọc là số lớn hơn 0";
    public static final String INVALID_PRICE = "Giá ship là số lớn hơn 0";
    public static final String INVALID_DATETIME = "Thời gian nhập không đúng";
    public static final String NULL_INFORMATION = "Cần điền thông tin";
    public static final String DUPLICATE_EMAIL = "Email này đã tồn tại";
    public static final String NOT_EXISTED_EMAIL = "Email không tồn tại";
    public static final String ACTIVE_CODE_MESSAGE = "Mã số kích hoạt đã được gửi vào địa chỉ email";

    public static final String OK = "Ok";
    public static int ST_ACCEPT_CODE = 0;
    public static int ST_REFUSE_CODE = 1;
    public static int SP_REQUEST_CODE = 2;
    public static int SP_CANCEL_CODE = 3;

    public static int ACTIVE_STATUS = 1;
    public static int NOT_ACTIVE_STATUS = 0;
    public static int MIN_PASS_LENGTH = 6;
    public static int MIN_PHONE_NUM_LENGTH = 9;
    public static int MAX_PHONE_NUM_LENGTH = 12;

    public static final String DEFAULT_URL = "http://192.168.0.102:3000/";
    public static final String PATH_TO_CONFIG_FILE = "text.txt";

    public static final String PATH_TO_LOGIN_INFORMATION_FILE = "loginInformation.txt";
}
