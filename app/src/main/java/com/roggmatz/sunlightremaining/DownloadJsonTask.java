package com.roggmatz.sunlightremaining;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import com.roggmatz.sunlightremaining.Sun;

/**
 * Created by Rogger on 11/16/2015.
 */
public class DownloadJsonTask extends AsyncTask<String, Integer, Sun> {
    private Context currentContext;
    private ProgressDialog dialog;

    public DownloadJsonTask(Context context) {
        currentContext = context;
    }
    protected void onPreExecute() {
        dialog = ProgressDialog.show(currentContext, currentContext.getString(R.string.progress_dialog_title),
                 "Foo", true);
        dialog.show();
    }
    protected void onPostExecute(Sun sun) {
        dialog.dismiss();
    }
    protected Sun doInBackground(String... strings) {
        String JSONResponse;
        JSONObject jsonObject;
        try {
            URL address = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) address.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inStream = connection.getInputStream();
            byte[] byte1024 = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while(inStream.read(byte1024) != -1) {
                byteArrayOutputStream.write(byte1024);
            }
            JSONResponse = new String(byteArrayOutputStream.toByteArray());
            jsonObject = new JSONObject(JSONResponse);

            return parseJson(jsonObject);
        }
        catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    private Sun parseJson(JSONObject rootObject) {
        Sun sun = new Sun();
        String[] desiredKeys = {"sunrise", "sunset"};
        JSONObject intermediateObject;
        int[] temporary_time = new int[2];

        try {
            rootObject = rootObject.getJSONObject("sun_phase");
        }
        catch (JSONException jsonNotFoundException) {
            Log.e("DownloadJsonTask.class", "sun_phase object not found in JSON root" +
                    " object.");
        }

        for(int i = 0; i < desiredKeys.length; i++) {
            try {
                intermediateObject = rootObject.getJSONObject(desiredKeys[i]);
                temporary_time[0] = intermediateObject.getInt("hour");
                temporary_time[1] = intermediateObject.getInt("minute");
            }
            catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            switch (desiredKeys[i]) {
                case "sunrise":
                    sun.setSunrise(temporary_time[0], temporary_time[1]);
                    break;
                case "sunset":
                    sun.setSunset(temporary_time[0], temporary_time[1]);
                    break;
            }
        }

        return sun;
    }
}
