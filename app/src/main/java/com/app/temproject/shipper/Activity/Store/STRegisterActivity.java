package com.app.temproject.shipper.Activity.Store;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Activity.LoginActivity;
import com.app.temproject.shipper.Fragment.Maps.MapsFragment;
import com.app.temproject.shipper.Fragment.Maps.WorkaroundMapFragment;
import com.app.temproject.shipper.Object.Store;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class STRegisterActivity extends AppCompatActivity {

    //map properties
//    private GoogleMap mMap;
//    //    MapView mMapView;
//    private Button btnSearch;
//    private EditText etSearch;
//    CameraPosition cameraPosition;
    private MapsFragment mapsFragment;

    //view properties
    private EditText etEmail;
    private TextView tvCheckEmail;
    private EditText etPassword;
    private TextView tvCheckPassWord;
    private EditText etConfirmPassword;
    private TextView tvCheckConfirmPassword;
    private EditText etStoreName;
    private TextView tvCheckStoreName;
    private EditText etStoreType;
    private TextView tvCheckStoreType;
    private EditText etPhoneNumber;
    private TextView tvCheckPhoneNumber;
    private EditText etStreet;
    private Spinner spDistrict;
    private Spinner spCountry;
    private Toolbar toolbar;
    private Spinner spCity;
    private Button btnRegister;
    private ScrollView svRegister;

    //globe variable properties
    private String email;
    private String password;
    private String confirmPassword;
    private String storeName;
    private String storeType;
    private String phoneNumber;
    private String street;
    private String district;
    private String city;
    private double longitude;
    private double latitude;
    private String country;
    private String[] citiesInVietNam;
    private String[] districtsInHaNoi;
    private String[] districtsInHoChiMinh;
    private String[] countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_activity_regrister);

        citiesInVietNam = getResources().getStringArray(R.array.cities_in_viet_nam);
        districtsInHaNoi = getResources().getStringArray(R.array.districts_in_ha_noi);
        districtsInHoChiMinh = getResources().getStringArray(R.array.districts_in_ho_chi_minh);
        countries = getResources().getStringArray(R.array.countries);
        initView();
        setEvent();

//
//        mMapView.onCreate(savedInstanceState);
//
//        mMapView.onResume();
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        try {
//            MapsInitializer.initialize(this.getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        mMapView.getMapAsync(this);
    }

    private void initView() {
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.info);
//        ByteArrayOutputStream baos=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
//        byte [] b=baos.toByteArray();
//        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.register));
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
        etEmail = (EditText) findViewById(R.id.etEmail);
        tvCheckEmail = (TextView) findViewById(R.id.tvCheckEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvCheckPassWord = (TextView) findViewById(R.id.tvCheckPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        tvCheckConfirmPassword = (TextView) findViewById(R.id.tvCheckConfirmPassword);
        etStoreName = (EditText) findViewById(R.id.etStoreName);
        tvCheckStoreName = (TextView) findViewById(R.id.tvCheckStoreName);
        etStoreType = (EditText) findViewById(R.id.etStoreType);
        tvCheckStoreType = (TextView) findViewById(R.id.tvCheckStoreType);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumbber);
        tvCheckPhoneNumber = (TextView) findViewById(R.id.tvCheckPhoneNumber);
        etStreet = (EditText) findViewById(R.id.etStreet);
        spDistrict = (Spinner) findViewById(R.id.spDistrict);
        spCity = (Spinner) findViewById(R.id.spCity);
        spCountry = (Spinner) findViewById(R.id.spCountry);
//        ArrayAdapter cityAdapter = new ArrayAdapter<>(STRegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, citiesInVietNam);
//        spCity.setAdapter(cityAdapter);
        ArrayAdapter countryAdapter = new ArrayAdapter<>(STRegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, countries);
        spCountry.setAdapter(countryAdapter);
        btnRegister = (Button) findViewById(R.id.btnRegister);
//        mMapView = (MapView) findViewById(R.id.mapView);
        svRegister = (ScrollView) findViewById(R.id.svRegister);
        mapsFragment = (MapsFragment) getSupportFragmentManager().findFragmentById(R.id.mapStRegister);

    }

    private void setEvent() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    confirmPassword = etConfirmPassword.getText().toString();
                    storeName = etStoreName.getText().toString();
                    storeType = etStoreType.getText().toString();
                    phoneNumber = etPhoneNumber.getText().toString();
                    street = etStreet.getText().toString();
                    district = spDistrict.getSelectedItem().toString();
                    city = spCity.getSelectedItem().toString();
                    country = spCountry.getSelectedItem().toString();
                    longitude = mapsFragment.getLongitude();
                    latitude = mapsFragment.getLatitude();

                    if (isValid(email, password, confirmPassword, storeName, storeType, phoneNumber,
                            street, city, district, longitude, latitude, country)) {

                        Store store = new Store(email, password, storeName, storeType, phoneNumber,
                                street, district, city, longitude, latitude, country);

                        String requestContent = new Gson().toJson(store);
                        new STRegisterAsyncTask(STRegisterActivity.this).execute(Constant.URL_SP_LOAD_REGISTERS, Constant.POST_METHOD, requestContent);
                    } else {
                        Toast.makeText(STRegisterActivity.this, Constant.INCORRECT_REGISTRATION_INFORMATION, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(STRegisterActivity.this, Constant.INCORRECT_REGISTRATION_INFORMATION, Toast.LENGTH_LONG).show();

                }
            }
        });

        mapsFragment.setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                svRegister.requestDisallowInterceptTouchEvent(true);
            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            ArrayAdapter<String> districtAdapter;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = spCity.getSelectedItem().toString();
                switch (city) {
                    case Constant.HA_NOI:
                        districtAdapter = new ArrayAdapter<>(STRegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, districtsInHaNoi);
                        spDistrict.setAdapter(districtAdapter);
                        break;
                    case Constant.HO_CHI_MINH:
                        districtAdapter = new ArrayAdapter<>(STRegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, districtsInHoChiMinh);
                        spDistrict.setAdapter(districtAdapter);
                        break;
                    default:
                        districtAdapter = new ArrayAdapter<>(STRegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, districtsInHaNoi);
                        spDistrict.setAdapter(districtAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            ArrayAdapter<String> cityAdapter;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = spCountry.getSelectedItem().toString();
                switch (country) {
                    case Constant.VIET_NAM:
                        cityAdapter = new ArrayAdapter<>(STRegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, citiesInVietNam);
                        spCity.setAdapter(cityAdapter);
                        break;
                    default:
                        cityAdapter = new ArrayAdapter<>(STRegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, citiesInVietNam);
                        spCity.setAdapter(cityAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private boolean isValid(String email, String password, String confirmPassword, String storeName,
                            String storeType, String phoneNumber, String street,
                            String city, String district, double longitude, double latitude, String country) {
        boolean isValidPhoneNumber;
        boolean isValidEmail;
        String regexPhoneNumber = "^[0-9]*$";
        String regexEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Matcher matcherPhoneNumber = Pattern.compile(regexPhoneNumber).matcher(phoneNumber.trim());
        Matcher matcherEmail = Pattern.compile(regexEmail).matcher(email);


        if(!(isValidPhoneNumber = matcherPhoneNumber.find())){
            tvCheckPhoneNumber.setText(Constant.INVALID_PHONE_NUMBER);
        }else{
            tvCheckPhoneNumber.setText("");
        }
        if(!(isValidEmail = matcherEmail.find())){
            tvCheckEmail.setText(Constant.INVALID_EMAIL);
        }else{
            tvCheckEmail.setText("");
        }
        if(password.length() < Constant.MIN_PASS_LENGTH){
            tvCheckPassWord.setText(Constant.INVALID_PASSWORD);
        }else{
            tvCheckPassWord.setText("");
        }
        if(!confirmPassword.equals(password)){
            tvCheckConfirmPassword.setText(Constant.INCORRECT_PASSWORD);
        }else{
            tvCheckConfirmPassword.setText("");
        }
        if(storeName.equals("")){
            tvCheckStoreName.setText(Constant.INVALID_STORE);
        }else{
            tvCheckStoreName.setText("");
        }

        if(!isValidEmail || !isValidPhoneNumber || (password.length() < Constant.MIN_PASS_LENGTH) ||
                (phoneNumber.trim().length() < Constant.MIN_PHONE_NUM_LENGTH) || (phoneNumber.trim().length() > Constant.MAX_PHONE_NUM_LENGTH)
                || !(confirmPassword.equals(password)) || storeName.equals("")){
            return false;
        }else{
            return true;
        }

    }

    private class STRegisterAsyncTask extends ServiceAsyncTask {
        public STRegisterAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if(error){
                Toast.makeText(STRegisterActivity.this, Constant.DUPLICATE_EMAIL, Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(STRegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
//        mMapView.onResume();
    }


}