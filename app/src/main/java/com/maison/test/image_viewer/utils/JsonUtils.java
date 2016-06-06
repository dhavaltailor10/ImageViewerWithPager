package com.maison.test.image_viewer.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Christophe on 26/05/2016.
 */
public class JsonUtils {

    public String getJsonHeroes() {
        String result = null;
        String[] params = new String[1];
        params[0] = PARAMS.WS_ADR_HEROES_URI;

        try {
            result = new RequestTask().execute(params).get();
        } catch (Exception e) {
            Log.d("AsyncTask error : ", e.getMessage());
        }

        return result;
    }

    private class RequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg0) {
            String jsonString;
            jsonString = getJSON(arg0[0]);
            return jsonString;
        }

        public String getJSON(String arg0) {
            HttpURLConnection httpUrlConnection;
            String result = null;

            try {
                String wsaddress = arg0;
                URL url = new URL(wsaddress);
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpUrlConnection.getInputStream(), "UTF-8"));

                int inChar;
                final StringBuilder readStr = new StringBuilder();

                while ((inChar = reader.read()) != -1) {
                    readStr.append((char) inChar);
                }

                result = readStr.toString();
                httpUrlConnection.disconnect();

                return result;
            } catch (Exception e) {
                Log.e("AsyncTask : ", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
