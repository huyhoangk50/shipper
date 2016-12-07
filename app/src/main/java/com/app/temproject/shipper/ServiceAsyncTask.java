package com.app.temproject.shipper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.app.temproject.shipper.ProjectVariable.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by paladin on 12/11/2016.
 */

public abstract class ServiceAsyncTask extends AsyncTask<String, String, String> {
    private Activity activity;
    ProgressDialog progressDialog;

    public ServiceAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!ConnectionDetector.isConnectingToInternet(activity)) {
            cancel(true);
            Toast.makeText(activity, activity.getString(R.string.internet_no_available), Toast.LENGTH_LONG).show();
        } else {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setTitle(R.string.app_name);
            progressDialog.setMessage(activity.getString(R.string.please_wait));
            progressDialog.show();
        }
    }

    protected abstract void processData(boolean error, String message, String data);


    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (!response.equals("")) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean error = jsonObject.getBoolean(Constant.KEY_ERROR);
                String message = jsonObject.getString(Constant.KEY_MESSAGE);
                String body = jsonObject.getString(Constant.KEY_DATA);
                processData(error, message, body);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(activity, Constant.NO_DATA_IS_RECEIVED, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... values) {
        BufferedReader reader = null;
        HttpURLConnection urlConnection = null;
        String response = "";

        try {
            URL url = new URL(values[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setRequestProperty(Constant.CONTENT_TYPE, Constant.JSON_TYPE);
            String method = values[1];
            urlConnection.setRequestMethod(method);
            if (method == Constant.POST_METHOD || method == Constant.PUT_METHOD) {
                urlConnection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
                String requestContent = values[2];
                outputStreamWriter.write(requestContent);
                outputStreamWriter.flush();
            }
            if (values[1] == Constant.DELETE_METHOD) {
                urlConnection.setRequestProperty(Constant.CONTENT_TYPE, "application/x-www-form-urlencoded");
            }
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }

            response = sb.toString();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}