package com.app.temproject.shipper.Libs;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;


import com.app.temproject.shipper.ProjectVariable.Constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by paladin on 09/12/2016.
 */

public class FileProcessing {
    public static final String readFileFromExternalStorage(String pathToFile){
        StringBuilder text = new StringBuilder();
        try{
            File sdcard = Environment.getExternalStorageDirectory();

            File file = new File(sdcard, pathToFile);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        if(text.toString().trim().equals("")){
            return Constant.DEFAULT_URL;
        }else {
            return text.toString().trim();
        }
    }

    public static void writeToInternalStorageFile(String data, String pathToFile, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(pathToFile, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }catch (Exception e){

        }
    }

    public static final String readFileFromInternalStorage(String pathToFile, Context context){
        String data = "";
        try {
            InputStream inputStream = context.openFileInput(pathToFile);
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                data = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void deleteInternalStorageFile(String pathToFile, Context context){
        File file = new File(context.getFilesDir(), pathToFile);
        file.delete();
    }
}
