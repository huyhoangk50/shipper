package com.app.temproject.shipper.Activity.Store;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.temproject.shipper.Fragment.Maps.MapsFragment;
import com.app.temproject.shipper.Fragment.Maps.WorkaroundMapFragment;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
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
    private boolean isEndTimeEarlierThanStartTime;

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

                }
            }
        });
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
        isEndTimeEarlierThanStartTime = checkEndTimeEarlierThanStartTime();
        isPhoneNumberValid = checkPhoneNumber();
    }

    private boolean checkEndTimeEarlierThanStartTime(){
        return true;
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
        if(isEndTimeEarlierThanStartTime) return false;
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
                //notify to user and go to main activity
            }
        }
    }

}
