package shire.the.great.http;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import shire.the.great.constants.Constants;

/**
 * Created by ZachS on 11/6/2016.
 */

public class DuinoHttpClient {

    public static HttpURLConnection getClient(String requestMethod,String requestUri) {
        URL url = null;
        try {
            url = new URL(requestUri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(requestMethod);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlConnection;
    }
}
