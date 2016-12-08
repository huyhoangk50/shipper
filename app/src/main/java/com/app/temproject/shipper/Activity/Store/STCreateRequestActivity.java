package com.app.temproject.shipper.Activity.Store;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Fragment.Maps.MapsFragment;
import com.app.temproject.shipper.Fragment.Maps.WorkaroundMapFragment;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
import com.google.gson.JsonObject;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class STCreateRequestActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private double distance;
    private String productName;
    private String deposit;
    private String price;
    private String destination;
    private String customerName;
    private String phoneNumber;
    private String endTime;
    private String startTime;
    private String endDate;
    private String startDate;
    private double longitude;
    private double latitude;

    private boolean isProductNameEmpty;
    private boolean isDepositBetterThanZero;
    private  boolean isPriceBetterThanZero;
    private boolean isDestinationEmpty;
    private boolean isCustomerNameEmpty;
    private boolean isPhoneNumberValid;
    private boolean isEndTimeLaterThanStartTime;

    private EditText etProductName;
    private EditText etDeposit;
    private EditText etPrice;
    private EditText etDestination;
    private  EditText etCustomerName;
    private EditText etPhoneNumber;
    private TextView tvEndDate;
    private TextView tvEndTime;
    private TextView tvStartTime;
    private TextView tvStartDate;
    private Button btnEndDate;
    private Button btnEndTime;
    private Button btnStartTime;
    private Button btnStartDate;
    private MapsFragment mapsFragment;
    private NestedScrollView nsvCreateRequest;

    private FloatingActionButton fabAddRequest;
    DatePickerDialog endDatePickerDialog;
    DatePickerDialog startDatePickerDialog;
    TimePickerDialog startTimePickerDialog;
    TimePickerDialog endTimePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_activity_create_request);

        initView();
        setEvent();
    }

    private void initView(){
        etProductName = (EditText) findViewById(R.id.etProductName);
        etDeposit = (EditText) findViewById(R.id.etDeposit);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etDestination = (EditText) findViewById(R.id.etDestination);
        etCustomerName = (EditText) findViewById(R.id.etCustomerName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btnEndDate = (Button) findViewById(R.id.btnEndDate);
        btnEndTime = (Button) findViewById(R.id.btnEndTime);
        btnStartTime = (Button) findViewById(R.id.btnStartTime);
        btnStartDate = (Button) findViewById(R.id.btnStartDate);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        fabAddRequest = (FloatingActionButton) findViewById(R.id.fabAddRequest);
        nsvCreateRequest = (NestedScrollView) findViewById(R.id.nsvCreateRequest);
        mapsFragment = (MapsFragment) getSupportFragmentManager().findFragmentById(R.id.mapsStCreateRequest);
    }

    private void setEvent(){
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                endDatePickerDialog = DatePickerDialog.newInstance(
                        STCreateRequestActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                endDatePickerDialog.setThemeDark(true);
                endDatePickerDialog.vibrate(true);
                endDatePickerDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                startDatePickerDialog = DatePickerDialog.newInstance(
                        STCreateRequestActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                startDatePickerDialog.setThemeDark(true);
                startDatePickerDialog.vibrate(true);
                startDatePickerDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                startTimePickerDialog = TimePickerDialog.newInstance(
                        STCreateRequestActivity.this,
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        true
                );
                startTimePickerDialog.setThemeDark(true);
                startTimePickerDialog.vibrate(true);
                startTimePickerDialog.show(getFragmentManager(), "Time picker dialog");
            }

        });

        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                endTimePickerDialog = TimePickerDialog.newInstance(
                        STCreateRequestActivity.this,
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        true
                );
                endTimePickerDialog.setThemeDark(true);
                endTimePickerDialog.vibrate(true);
                endTimePickerDialog.show(getFragmentManager(), "Time picker dialog");
            }

        });

        mapsFragment.setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                nsvCreateRequest.requestDisallowInterceptTouchEvent(true);
            }
        });

        fabAddRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInformationFromUser();
                checkInformationCorrectness();
                if(!isAllInformationCorrect()){
                    notifyToUser();
                } else {
                    //fake data

//                    Store store = new Store(1, "huyhaongk4", "h32o", "nguyen huy hoang", "033884", "Tap hóa", "số 3 tân mai", "hoàng mai", "hà nội", 21.0212, 105.2344, "Việt nam");
//                    store.setStatus(Constant.ACTIVE_STATUS);
//                    ProjectManagement.store = store;

                    distance = getDistanceFromStoreToDestination();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(Constant.KEY_DISTANCE, distance);
                    jsonObject.addProperty(Constant.KEY_DEPOSIT, deposit);
                    jsonObject.addProperty(Constant.KEY_START_TIME, startDate + " " + startTime);
                    jsonObject.addProperty(Constant.KEY_END_TIME, endDate + " " + endTime);
                    jsonObject.addProperty(Constant.KEY_STORE_ID, ProjectManagement.store.getId());
                    jsonObject.addProperty(Constant.KEY_DESTINATION, destination);
                    jsonObject.addProperty(Constant.KEY_PRICE, price);
                    jsonObject.addProperty(Constant.KEY_PRODUCT_NAME, productName);
                    jsonObject.addProperty(Constant.KEY_PHONE_NUMBER, phoneNumber);
                    jsonObject.addProperty(Constant.KEY_LONGITUDE, longitude);
                    jsonObject.addProperty(Constant.KEY_LATITUDE, latitude);
                    jsonObject.addProperty(Constant.KEY_CUSTOMER_NAME, customerName);

                    new CreateRequestAsyncTask(STCreateRequestActivity.this).execute(Constant.URL_ST_CREATE_REQUEST, Constant.POST_METHOD, jsonObject.toString());

                }
            }
        });
    }

    private double getDistanceFromStoreToDestination(){
        Location startLocation = new Location("");
        startLocation.setLongitude(ProjectManagement.store.getLongitude());
        startLocation.setLatitude(ProjectManagement.store.getLatitude());
        startLocation.setLatitude(latitude);
        Location destination = new Location("");
        destination.setLongitude(longitude);
        destination.setLatitude(latitude);
        return startLocation.distanceTo(destination);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if(view == endTimePickerDialog){
            endTime = hourOfDay + ":" + minute + ":" + second;
            tvEndTime.setText(endTime);
        }
        if(view == startTimePickerDialog){
            startTime = hourOfDay + ":" + minute + ":" + second;
            tvStartTime.setText(startTime);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if(view == endDatePickerDialog){
            endDate = year + "/" + monthOfYear + "/" + dayOfMonth;
            tvEndDate.setText(endDate);
        }
        if(view == startDatePickerDialog){
            startDate = year + "/" + monthOfYear + "/" + dayOfMonth;
            tvStartDate.setText(startDate);
        }
    }

    private void setInformationFromUser(){
        productName = etProductName.getText().toString();
        deposit = etDeposit.getText().toString();
        price = etPrice.getText().toString();
        destination = etDestination.getText().toString();
        customerName = etCustomerName.getText().toString();
        phoneNumber = etPhoneNumber.getText().toString();
        endTime = tvEndTime.getText().toString();
        startDate = tvStartDate.getText().toString();
        startTime = tvStartTime.getText().toString();
        endDate = tvEndDate.getText().toString();
        latitude = mapsFragment.getLatitude();
        longitude = mapsFragment.getLongitude();
    }
    private void checkInformationCorrectness(){
        isProductNameEmpty = productName.equals("");
        isCustomerNameEmpty = customerName.equals("");
        isDepositBetterThanZero = (Integer.valueOf(deposit) > 0);
        isPriceBetterThanZero = Integer.valueOf(price) > 0;
        isDestinationEmpty = destination.equals("");
        isEndTimeLaterThanStartTime = checkEndTimeEarlierThanStartTime();
        isPhoneNumberValid = checkPhoneNumber();
    }

    private boolean checkEndTimeEarlierThanStartTime(){
        return false;
    }
    private boolean checkPhoneNumber(){
        return true;
    }

    private boolean isAllInformationCorrect(){
        if(isProductNameEmpty) return false;
        if(isCustomerNameEmpty) return false;
        if(!isDepositBetterThanZero) return false;
        if(!isPriceBetterThanZero) return false;
        if(isDestinationEmpty) return false;
        if(isEndTimeLaterThanStartTime) return false;
        if(!isPhoneNumberValid) return false;
        return true;
    }
    private void notifyToUser(){

    }

    private class CreateRequestAsyncTask extends ServiceAsyncTask {
        public CreateRequestAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if(error){
                //notify to user
            } else{
                Toast.makeText(STCreateRequestActivity.this, getString(R.string.create_request_successfully), Toast.LENGTH_LONG).show();
                finish();
                //notify to user and go to main activity
            }
        }
    }

}