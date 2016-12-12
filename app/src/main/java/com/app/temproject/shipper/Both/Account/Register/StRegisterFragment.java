package com.app.temproject.shipper.Both.Account.Register;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Both.Account.Login.LoginActivity;
import com.app.temproject.shipper.Libs.CheckingInformation;
import com.app.temproject.shipper.Libs.Maps.MapsFragment;
import com.app.temproject.shipper.Libs.Maps.WorkaroundMapFragment;
import com.app.temproject.shipper.Object.Store;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.Libs.ServiceAsyncTask;
import com.google.gson.Gson;

/**
 * Created by Admin on 12/12/2016.
 */

public class StRegisterFragment extends Fragment {
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
    private View rootView;

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

    private boolean isValidPhoneNumber ;
    private boolean isValidEmail;
    private boolean isValidPassword;
    private boolean isValidConfirmPassword;
    private boolean isValidStoreName;

    @Override
    public void onCreate ( Bundle savedInstanceState )    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        rootView = inflater.inflate(R.layout.st_register_fragment, container, false);
        getdata();
        initView();
        setEvent();
        return rootView;
    }
    private void initView() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.register));
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                getActivity().finish();
            }
        });
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        tvCheckEmail = (TextView) rootView.findViewById(R.id.tvCheckEmail);
        etPassword = (EditText) rootView.findViewById(R.id.etPassword);
        tvCheckPassWord = (TextView) rootView.findViewById(R.id.tvCheckPassword);
        etConfirmPassword = (EditText) rootView.findViewById(R.id.etConfirmPassword);
        tvCheckConfirmPassword = (TextView) rootView.findViewById(R.id.tvCheckConfirmPassword);
        etStoreName = (EditText) rootView.findViewById(R.id.etStoreName);
        tvCheckStoreName = (TextView) rootView.findViewById(R.id.tvCheckStoreName);
        etStoreType = (EditText) rootView.findViewById(R.id.etStoreType);
        tvCheckStoreType = (TextView) rootView.findViewById(R.id.tvCheckStoreType);
        etPhoneNumber = (EditText) rootView.findViewById(R.id.etPhoneNumber);
        tvCheckPhoneNumber = (TextView) rootView.findViewById(R.id.tvCheckPhoneNumber);
        etStreet = (EditText) rootView.findViewById(R.id.etStreet);
        spDistrict = (Spinner) rootView.findViewById(R.id.spDistrict);
        spCity = (Spinner) rootView.findViewById(R.id.spCity);
        spCountry = (Spinner) rootView.findViewById(R.id.spCountry);
//        ArrayAdapter cityAdapter = new ArrayAdapter<>(STRegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, citiesInVietNam);
//        spCity.updateUI(cityAdapter);
        ArrayAdapter countryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, countries);
        spCountry.setAdapter(countryAdapter);
        btnRegister = (Button) getActivity().findViewById(R.id.btnRegister);
//        mMapView = (MapView) findViewById(R.id.mapView);
        svRegister = (ScrollView) getActivity().findViewById(R.id.svRegister);
        mapsFragment = (MapsFragment) getChildFragmentManager().findFragmentById(R.id.mapStRegister);
        if(mapsFragment == null){
            mapsFragment = new MapsFragment();
        }

    }
    private void setEvent() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setInformationFromUser();
                    checkInformationCorrectness();
                    if (isAllInformationCorrect()) {

                        Store store = new Store(email, password, storeName, phoneNumber, storeType,
                                street, district, city, longitude, latitude, country);

                        String requestContent = new Gson().toJson(store);
                        new STRegisterAsyncTask(getActivity()).execute(ProjectManagement.urlSpRegister, Constant.POST_METHOD, requestContent);
                    } else {
                        notifyToUser();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), Constant.INCORRECT_REGISTRATION_INFORMATION, Toast.LENGTH_LONG).show();

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
                        districtAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtsInHaNoi);
                        spDistrict.setAdapter(districtAdapter);
                        break;
                    case Constant.HO_CHI_MINH:
                        districtAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtsInHoChiMinh);
                        spDistrict.setAdapter(districtAdapter);
                        break;
                    default:
                        districtAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districtsInHaNoi);
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
                        cityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, citiesInVietNam);
                        spCity.setAdapter(cityAdapter);
                        break;
                    default:
                        cityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, citiesInVietNam);
                        spCity.setAdapter(cityAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
    private void setInformationFromUser() {
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
    }
    private void checkInformationCorrectness() {

        isValidPhoneNumber = CheckingInformation.isValidPhoneNumber(phoneNumber);
        isValidEmail = CheckingInformation.isValidEmail(email);
        isValidPassword = CheckingInformation.isValidPassword(password);
        isValidConfirmPassword = confirmPassword.equals(password);
        isValidStoreName = !CheckingInformation.isEmpty(storeName);
    }
    private boolean isAllInformationCorrect() {
        if (!isValidPhoneNumber) return false;
        if (!isValidEmail) return false;
        if (!isValidPassword) return false;
        if (!isValidConfirmPassword) return false;
        if (!isValidStoreName) return false;

        return true;
    }
    private void notifyToUser() {
        if (!isValidPhoneNumber) {
            tvCheckPhoneNumber.setText(Constant.INVALID_PHONE_NUMBER);
        } else {
            tvCheckPhoneNumber.setText("");
        }

        if (!isValidEmail) {
            tvCheckEmail.setText(Constant.INVALID_EMAIL);
        }else{
            tvCheckEmail.setText("");
        }

        if (!isValidPassword){
            tvCheckPassWord.setText(Constant.INVALID_PASSWORD);
        }else {
            tvCheckPassWord.setText("");
        }

        if (!isValidConfirmPassword) {
            tvCheckConfirmPassword.setText(Constant.INCORRECT_PASSWORD);
        }else{
            tvCheckConfirmPassword.setText("");
        }

        if (!isValidStoreName) {
            tvCheckStoreName.setText(Constant.INVALID_STORE);
        }else{
            tvCheckStoreName.setText("");
        }
        Toast.makeText(getActivity(), Constant.INCORRECT_REGISTRATION_INFORMATION, Toast.LENGTH_LONG).show();
    }
    private class STRegisterAsyncTask extends ServiceAsyncTask {
        public STRegisterAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if(error){
                Toast.makeText(getActivity(), Constant.DUPLICATE_EMAIL, Toast.LENGTH_LONG).show();
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage(Constant.ACTIVE_CODE_MESSAGE);

                alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1){

                        Intent intent = new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    }
                });

                alertDialogBuilder.create().show();
            }
        }

    }
    private void getdata(){
        citiesInVietNam = getResources().getStringArray(R.array.cities_in_viet_nam);
        districtsInHaNoi = getResources().getStringArray(R.array.districts_in_ha_noi);
        districtsInHoChiMinh = getResources().getStringArray(R.array.districts_in_ho_chi_minh);
        countries = getResources().getStringArray(R.array.countries);
    }
}
