package shire.the.great.nodepi.tasks;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import shire.the.great.http.DuinoHttpClient;

/**
 * Created by ZachS on 11/6/2016.
 */

public class GetJsonTask extends AsyncTask<String, Integer, JSONObject> {
    private AsyncResult<JSONObject> delegate = null;

    public GetJsonTask(AsyncResult<JSONObject> result) {
        this.delegate = result;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String url = strings[0];
        HttpURLConnection connection = DuinoHttpClient.getClient("GET", url);
        StringBuilder responseStrBuilder = new StringBuilder();
        try {
            connection.connect();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            in.close();
            streamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(responseStrBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        delegate.onComplete(jsonObject);
    }
}
