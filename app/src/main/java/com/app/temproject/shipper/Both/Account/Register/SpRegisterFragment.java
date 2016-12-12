package com.app.temproject.shipper.Both.Account.Register;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Both.Account.Login.LoginActivity;
import com.app.temproject.shipper.Libs.CheckingInformation;
import com.app.temproject.shipper.Libs.Maps.MapsFragment;
import com.app.temproject.shipper.Libs.Maps.WorkaroundMapFragment;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.Libs.ServiceAsyncTask;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Admin on 12/12/2016.
 */

public class SpRegisterFragment extends Fragment {
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    final private int MAX_WIDTH = 1000;
    final private int MAX_HEIGHT = 1000;
    final private String AVATAR_DEFAULT = "userdefault.jpg";
    final private String EXTENSION_DEFAULT = "jpg";


    private ScrollView svRegister;
    private MapsFragment mapsFragment;
    private EditText etDate;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etName;
    private EditText etPhoneNumber;
    private EditText etAddress;
    private EditText etBirthDay;

    private TextView tvCheckShipperName;
    private TextView tvCheckEmail;
    private TextView tvCheckPassword;
    private TextView tvCheckConfirmPassword;
    private TextView tvCheckPhoneNumber;
    private TextView tvCheckAddress;

    private boolean isValidShipperName;
    private boolean isValidEmail;
    private boolean isValidPassword;
    private boolean isValidConfirmPassword;
    private boolean isValidPhoneNumber;
    private boolean isValidAddress;

    private String email;
    private String password;
    private String confirmPassword;
    private String shipperName;
    private String phoneNumber;
    private String address;
    private String birthday;
    private String imageStringBase64;
    private String imageExtension;
    private double longitude;
    private double latitude;

//    private Button btnUploadAvatar;
    private Button btnRegister;
    private ImageView ivUploadAvatar;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    View rootView;
    @Override
    public void onCreate ( Bundle savedInstanceState )    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        rootView = inflater.inflate(R.layout.sp_register_fragment, container, false);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        initView();
        setDateTimeField();
        setEvet();
        return rootView;
    }

    private void initView(){
        etDate = (EditText) rootView.findViewById(R.id.etDateOfBirth);
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.requestFocus();
        svRegister = (ScrollView) rootView.findViewById(R.id.svRegister);

        ivUploadAvatar = (ImageView) rootView.findViewById(R.id.ivUpload);

        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);

        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        etPassword = (EditText) rootView.findViewById(R.id.etSP_Password);
        etConfirmPassword = (EditText) rootView.findViewById(R.id.etConfirmPassword);
        etName = (EditText) rootView.findViewById(R.id.etName);
        etPhoneNumber = (EditText) rootView.findViewById(R.id.etPhoneNumber);
        etAddress = (EditText) rootView.findViewById(R.id.etAddress);
        etBirthDay = (EditText) rootView.findViewById(R.id.etDateOfBirth);

        tvCheckShipperName = (TextView) rootView.findViewById(R.id.tvCheckShipperName);
        tvCheckEmail = (TextView) rootView.findViewById(R.id.tvCheckEmail);
        tvCheckPassword = (TextView) rootView.findViewById(R.id.tvCheckPassword);
        tvCheckConfirmPassword = (TextView) rootView.findViewById(R.id.tvCheckConfirmPassword);
        tvCheckPhoneNumber = (TextView) rootView.findViewById(R.id.tvCheckPhoneNumber);
        tvCheckAddress = (TextView) rootView.findViewById(R.id.tvCheckAddress);

        mapsFragment = (MapsFragment) getChildFragmentManager().findFragmentById(R.id.mapSPRegister);

        if(mapsFragment == null){
            mapsFragment = new MapsFragment();
        }
        mapsFragment.setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                svRegister.requestDisallowInterceptTouchEvent(true);
            }
        });
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Uri selectedImage = data.getData();
                //Log.i(Tag, "" + selectedImage.toString());
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                //Log.i(TAG, "" + picturePath);

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                ivUploadAvatar.setImageBitmap(getResizedBitmap(bitmap));

                BitmapDrawable drawable = (BitmapDrawable) ivUploadAvatar.getDrawable();
                Bitmap bitmap64 = drawable.getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap64.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] bb = bos.toByteArray();

                imageStringBase64 = Base64.encodeToString(bb, 0);
                imageExtension = picturePath;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public Bitmap getResizedBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scale = 0;
        if (width >= height) {
            scale = ((float) MAX_WIDTH) / width;
        } else scale = ((float) MAX_HEIGHT) / height;
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scale, scale);

        // "RECREATE" THE NEW_REQUEST BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    private void setEvet(){

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        ivUploadAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInformationFromShipper();
                checkInformationCorrectness();
                if(isAllInformationCorrect()) {
                    JsonObject jsonObject = createHttpBody();
                    new RegisterAsyncTask(getActivity()).execute(ProjectManagement.urlSpRegister, Constant.POST_METHOD, jsonObject.toString());
                }else {
                    notifyToUser();
                }
            }
        });
    }
    private JsonObject createHttpBody(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constant.KEY_EMAIL, email);
        jsonObject.addProperty(Constant.KEY_PASSWORD, password);
        jsonObject.addProperty(Constant.KEY_NAME, confirmPassword);
        jsonObject.addProperty(Constant.KEY_PHONE_NUMBER, phoneNumber);
        jsonObject.addProperty(Constant.KEY_ADDRESS, address);
        jsonObject.addProperty(Constant.KEY_BIRTHDAY, birthday);
        jsonObject.addProperty(Constant.KEY_ROLE, Constant.SHIPPER_ROLE);
        jsonObject.addProperty(Constant.KEY_LONGITUDE, longitude);
        jsonObject.addProperty(Constant.KEY_LATITUDE, latitude);
        jsonObject.addProperty(Constant.KEY_NAME, shipperName);
        if (imageStringBase64 == null) {

            jsonObject.addProperty(Constant.KEY_AVATAR, AVATAR_DEFAULT);
        } else {

            if (imageExtension == null) {
                jsonObject.addProperty(Constant.KEY_IMAGE_EXTENSION, EXTENSION_DEFAULT);
            } else {
                jsonObject.addProperty(Constant.KEY_IMAGE_EXTENSION, imageExtension);
            }
            jsonObject.addProperty(Constant.KEY_IMAGE_BASE_64_STRING, imageStringBase64);
        }
        return jsonObject;
    }
    private void setInformationFromShipper() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        confirmPassword = etConfirmPassword.getText().toString();
        shipperName = etName.getText().toString();
        phoneNumber = etPhoneNumber.getText().toString();
        address = etAddress.getText().toString();
        birthday = etBirthDay.getText().toString();
        longitude = mapsFragment.getLongitude();
        latitude = mapsFragment.getLatitude();
    }
    private void checkInformationCorrectness(){
        isValidShipperName = !CheckingInformation.isEmpty(shipperName);
        isValidEmail = CheckingInformation.isValidEmail(email);
        isValidPassword = CheckingInformation.isValidPassword(password);
        isValidConfirmPassword = confirmPassword.equals(password);
        isValidPhoneNumber = CheckingInformation.isValidPhoneNumber(phoneNumber);
        isValidAddress = !CheckingInformation.isEmpty(address);
    }
    private boolean isAllInformationCorrect(){
        if(!isValidShipperName) return false;
        if(!isValidEmail) return false;
        if(!isValidPassword) return false;
        if(!isValidConfirmPassword) return false;
        if(!isValidPhoneNumber) return false;
        if(!isValidAddress) return false;
        return true;
    }
    private void notifyToUser(){
        if(!isValidShipperName){
            tvCheckShipperName.setText(Constant.NULL_INFORMATION);
        }else {
            tvCheckShipperName.setText("");
        }

        if(!isValidEmail){
            tvCheckEmail.setText(Constant.INVALID_EMAIL);
        }else {
            tvCheckEmail.setText("");
        }

        if(!isValidPassword){
            tvCheckPassword.setText(Constant.INVALID_PASSWORD);
        }else {
            tvCheckPassword.setText("");
        }

        if(!isValidConfirmPassword){
            tvCheckConfirmPassword.setText(Constant.INCORRECT_PASSWORD);
        }else {
            tvCheckConfirmPassword.setText("");
        }

        if(!isValidPhoneNumber){
            tvCheckPhoneNumber.setText(Constant.INVALID_PHONE_NUMBER);
        }else{
            tvCheckPhoneNumber.setText("");
        }

        if(!isValidAddress){
            tvCheckAddress.setText(Constant.NULL_INFORMATION);
        }else {
            tvCheckAddress.setText("");
        }

        Toast.makeText(getActivity(), Constant.INCORRECT_REGISTRATION_INFORMATION, Toast.LENGTH_LONG).show();
    }
    private class RegisterAsyncTask extends ServiceAsyncTask {

        public RegisterAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if(error){
                Toast.makeText(getActivity(), Constant.DUPLICATE_EMAIL, Toast.LENGTH_LONG).show();
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage(Constant.ACTIVE_CODE_MESSAGE);
                alertDialogBuilder.setNeutralButton(Constant.OK, new DialogInterface.OnClickListener(){
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
}
