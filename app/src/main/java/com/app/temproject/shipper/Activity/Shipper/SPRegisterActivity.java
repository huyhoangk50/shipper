package com.app.temproject.shipper.Activity.Shipper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Activity.LoginActivity;
import com.app.temproject.shipper.Activity.Store.STRegisterActivity;
import com.app.temproject.shipper.CheckingInformation;
import com.app.temproject.shipper.Fragment.Maps.MapsFragment;
import com.app.temproject.shipper.Fragment.Maps.WorkaroundMapFragment;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by huyhoang on 12/08/2016.
 */
public class SPRegisterActivity extends AppCompatActivity implements OnClickListener {

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

    private Button btnUploadAvatar;
    private Button btnRegister;
    private ImageView ivUploadAvatar;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_activity_register);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        initView();

        setDateTimeField();

        btnUploadAvatar.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Uri selectedImage = data.getData();
                //Log.i(Tag, "" + selectedImage.toString());
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                //Log.i(TAG, "" + picturePath);

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
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


    private void initView() {
        etDate = (EditText) findViewById(R.id.etDateOfBirth);
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.requestFocus();
        svRegister = (ScrollView) findViewById(R.id.svRegister);
        btnUploadAvatar = (Button) findViewById(R.id.btnUpload);

        ivUploadAvatar = (ImageView) findViewById(R.id.ivUpload);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etSP_Password);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etName = (EditText) findViewById(R.id.etSP_Name);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etBirthDay = (EditText) findViewById(R.id.etDateOfBirth);

        tvCheckShipperName = (TextView) findViewById(R.id.tvCheckShipperName);
        tvCheckEmail = (TextView) findViewById(R.id.tvCheckEmail);
        tvCheckPassword = (TextView) findViewById(R.id.tvCheckPassword);
        tvCheckConfirmPassword = (TextView) findViewById(R.id.tvCheckConfirmPassword);
        tvCheckPhoneNumber = (TextView) findViewById(R.id.tvCheckPhoneNumber);
        tvCheckAddress = (TextView) findViewById(R.id.tvCheckAddress);

        mapsFragment = (MapsFragment) getSupportFragmentManager().findFragmentById(R.id.mapSPRegister);

        mapsFragment.setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                svRegister.requestDisallowInterceptTouchEvent(true);
            }
        });
    }

    private void setDateTimeField() {
        etDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {

        if (view == btnUploadAvatar) {
            showFileChooser();
        }
        if (view == etDate) {
            datePickerDialog.show();
        }
        if (view == btnRegister) {
            setInformationFromShipper();
            checkInformationCorrectness();
            if(isAllInformationCorrect()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(Constant.KEY_EMAIL, email);
                jsonObject.addProperty(Constant.KEY_PASSWORD, password);
                jsonObject.addProperty(Constant.KEY_NAME, confirmPassword);
                jsonObject.addProperty(Constant.KEY_PHONE_NUMBER, phoneNumber);
                jsonObject.addProperty(Constant.KEY_ADDRESS, address);
                jsonObject.addProperty(Constant.KEY_BIRTHDAY, birthday);
                jsonObject.addProperty(Constant.KEY_ROLE, ProjectManagement.shipper.getRole());
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
                new RegisterAsyncTask(SPRegisterActivity.this).execute(ProjectManagement.urlSpRegister, Constant.POST_METHOD, jsonObject.toString());
            }else {
                notifyToUser();
            }
        }

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

        Toast.makeText(SPRegisterActivity.this, Constant.INCORRECT_REGISTRATION_INFORMATION, Toast.LENGTH_LONG).show();
    }
    private class RegisterAsyncTask extends ServiceAsyncTask {

        public RegisterAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if(error){
                Toast.makeText(SPRegisterActivity.this, Constant.DUPLICATE_EMAIL, Toast.LENGTH_LONG).show();
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SPRegisterActivity.this);
                alertDialogBuilder.setMessage(Constant.ACTIVE_CODE_MESSAGE);

                alertDialogBuilder.setNeutralButton(Constant.OK, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1){

                        Intent intent = new Intent(SPRegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });

                alertDialogBuilder.create().show();
            }
        }
    }
}