package com.example.androidcourse.data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class PostRequest  extends android.os.AsyncTask<String, Void, String> {

    String url;
    ArrayList<String> postData;
    Callable<Void> onPostExecuteFunction;
    boolean showLoadingDialog;

    public String getResult() {
        return result;
    }

    String result;

    public PostRequest(String url, ArrayList<String> postData, boolean showLoadingDialog) {
        this.url = url;
        this.postData = postData;
        this.showLoadingDialog = showLoadingDialog;
    }

    public void setOnPostExecuteFunction(Callable<Void> onPostExecuteFunction) {
        this.onPostExecuteFunction = onPostExecuteFunction;
    }



    @Override
    protected String doInBackground(String... strings) {
        try {
            String URL = this.url;

            java.net.URL url = new URL(URL);
            Log.println(Log.DEBUG,"PostRequest","URL is: "+URL);

            HttpURLConnection httpurlconn = (HttpURLConnection) url.openConnection();
            httpurlconn.setRequestMethod("POST");
            httpurlconn.setDoOutput(true);
            httpurlconn.setDoInput(true);
            OutputStream outputStream = httpurlconn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data ="";
            for (int i = 0; i<this.postData.size(); i++) {
                post_data +=URLEncoder.encode(this.postData.get(i), "UTF-8") + "=" + URLEncoder.encode(this.postData.get(++i), "UTF-8") +"&";

            }
            Log.println(Log.DEBUG,"PostRequest","PostData is: "+post_data);

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpurlconn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpurlconn.disconnect();

            return result;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    @Override
    protected void onPreExecute() {



    }

    @Override
    protected void onPostExecute(String result) {
        Log.println(Log.DEBUG,"PostRequest","Result is: "+result);
        this.result = result;
        try {
            this.onPostExecuteFunction.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
