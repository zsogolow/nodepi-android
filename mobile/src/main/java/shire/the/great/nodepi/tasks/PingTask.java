package shire.the.great.nodepi.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;

import shire.the.great.http.DuinoHttpClient;
import shire.the.great.http.DuinoExecution;

/**
 * Created by ZachS on 11/6/2016.
 */

public class PingTask extends AsyncTask<DuinoExecution, Integer, Boolean> {
    private AsyncResult<Boolean> delegate;

    public PingTask(AsyncResult<Boolean> result) {
        this.delegate = result;
    }

    @Override
    protected Boolean doInBackground(DuinoExecution... executions) {
        DuinoExecution execution = executions[0];
        HttpURLConnection connection =
                DuinoHttpClient.getClient("GET", execution.getUri() + "?id=" + execution.getId());
        int statusCode = -1;
        try {
            connection.connect();
            statusCode = connection.getResponseCode();
            String message = connection.getResponseMessage();
        } catch (IOException e) {
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
