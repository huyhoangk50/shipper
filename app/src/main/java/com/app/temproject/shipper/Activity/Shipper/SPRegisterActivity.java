package com.app.temproject.shipper.Activity.Shipper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.temproject.shipper.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by huyhoang on 12/08/2016.
 */
public class SPRegisterActivity extends Activity implements OnClickListener {

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String bitmap_base64;
    private String ext;
    final private int MAX_WIDTH = 1000;
    final private int MAX_HEIGHT = 1000;


    private EditText DateEtxt;
    private EditText etSP_Email;
    private EditText etSP_Password;
    private EditText etSP_Name;
    private EditText etSP_PhoneNumber;
    private EditText etSp_Address;
    private EditText etSP_Birthofdate;
    private String role = "1";

    private Button btnSPUploadAvatar ;
    private Button btnSP_register;
    private ImageView ivSPUploadAvatar;

    private DatePickerDialog DatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_activity_register);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();

        btnSPUploadAvatar.setOnClickListener(this);
        btnSP_register.setOnClickListener(this);
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
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                //Log.i(TAG, "" + picturePath);

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                ivSPUploadAvatar.setImageBitmap(getResizedBitmap(bitmap));

                BitmapDrawable drawable = (BitmapDrawable) ivSPUploadAvatar.getDrawable();
                Bitmap bitmap64 = drawable.getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap64.compress(Bitmap.CompressFormat.PNG,100,bos);
                byte[] bb = bos.toByteArray();

                bitmap_base64 = Base64.encodeToString(bb,0);
                ext = picturePath;

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

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    private void saveToJson(){
        //String ext = (String) ivSPUploadAvatar.getTag(1);

        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("email", etSP_Email.getText().toString());
        valuesMap.put("password", etSP_Password.getText().toString());
        valuesMap.put("name", etSP_Name.getText().toString());
        valuesMap.put("phoneNumber", etSP_PhoneNumber.getText().toString());
        valuesMap.put("address", etSp_Address.getText().toString());
        if(bitmap_base64==null){
            valuesMap.put("avatar","userdefault.jpg" );
        }
        else {
            valuesMap.put("birthday", etSP_Birthofdate.getText().toString());
            valuesMap.put("role", role);
            if (ext == null) {
                valuesMap.put("imageExtension", "jpg");
            } else {
                valuesMap.put("imageExtension", ext);
            }
            valuesMap.put("imageBase64String", bitmap_base64);
        }

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(valuesMap);

        Toast.makeText(SPRegisterActivity.this, json,
                Toast.LENGTH_LONG).show();
        //System.out.println(json);
    }

    private void findViewsById() {
        DateEtxt = (EditText) findViewById(R.id.etSP_dateofbirth);
        DateEtxt.setInputType(InputType.TYPE_NULL);
        DateEtxt.requestFocus();

        btnSPUploadAvatar = (Button) findViewById(R.id.btnSP_Upload);

        ivSPUploadAvatar = (ImageView) findViewById(R.id.ivSP_Upload);

        btnSP_register = (Button) findViewById(R.id.btnSP_Register);

        etSP_Email = (EditText) findViewById(R.id.etSP_Email);
        etSP_Password = (EditText) findViewById(R.id.etSP_Password);
        etSP_Name = (EditText) findViewById(R.id.etSP_Name);
        etSP_PhoneNumber = (EditText) findViewById(R.id.etSP_PhoneNumber);
        etSp_Address = (EditText) findViewById(R.id.etSP_address);
        etSP_Birthofdate = (EditText) findViewById(R.id.etSP_dateofbirth);



    }

    private void setDateTimeField() {
        DateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DateEtxt.setText(dateFormatter.format(newDate.getTime()));
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

        if(view == btnSPUploadAvatar){
            showFileChooser();
        }

        if (view == DateEtxt) {
            DatePickerDialog.show();
        }

        if(view== btnSP_register){
            saveToJson();
        }
    }
}
