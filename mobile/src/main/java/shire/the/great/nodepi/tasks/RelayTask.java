package shire.the.great.nodepi.tasks;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import shire.the.great.duinos.actions.DuinoActions;
import shire.the.great.http.DuinoHttpClient;
import shire.the.great.http.DuinoExecution;

/**
 * Created by ZachS on 11/6/2016.
 */

public class RelayTask extends AsyncTask<DuinoExecution, Integer, Boolean> {
    private AsyncResult<Boolean> delegate;

    public RelayTask(AsyncResult<Boolean> result) {
        this.delegate = result;
    }

    @Override
    protected Boolean doInBackground(DuinoExecution... executions) {
        DuinoExecution execution = executions[0];
        HttpURLConnection connection = null;
        int statusCode = -1;
        try {
            if (execution.getAction() == DuinoActions.RelayOff || execution.getAction() == DuinoActions.RelayOn) {
                connection = DuinoHttpClient.getClient("POST", execution.getUri());
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", execution.getId()+"");
                out.write(jsonObject.toString().getBytes("UTF-8"));
                out.close();
            } else if (execution.getAction() == DuinoActions.RelayState) {
                connection = DuinoHttpClient.getClient("GET", execution.getUri() + "?id=" + execution.getId());
            }

            connection.connect();
            statusCode = connection.getResponseCode();
            String message = connection.getResponseMessage();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        if (statusCode == 200)
            return true;
        else
            return false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        delegate.onComplete(aBoolean);
    }
}
