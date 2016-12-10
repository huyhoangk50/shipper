package com.app.temproject.shipper.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;


import com.app.temproject.shipper.ProjectVariable.Constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by paladin on 09/12/2016.
 */

public class FileProcessing {
    public static final String readFileFromExternalStorager(String pathToFile){
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


}
