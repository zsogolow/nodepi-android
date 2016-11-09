package shire.the.great.nodepi.tasks;

import android.os.AsyncTask;

import shire.the.great.duinos.Duino;
import shire.the.great.sockets.DuinoPackage;
import shire.the.great.sockets.HandledPackage;
import shire.the.great.sockets.PackageHandler;

/**
 * Created by ZachS on 11/6/2016.
 */

public class PackageHandlerTask extends AsyncTask<DuinoPackage, Integer, HandledPackage> {
    private AsyncResult<HandledPackage> delegate;

    public PackageHandlerTask(AsyncResult<HandledPackage> result) {
        this.delegate = result;
    }

    @Override
    protected HandledPackage doInBackground(DuinoPackage... packages) {
        PackageHandler handler = new PackageHandler();
        DuinoPackage duinoPackage = packages[0];
        Duino duino = handler.handle(duinoPackage);
        return new HandledPackage(duinoPackage.getRawPackage(),
                duinoPackage.getPackageType(), duino);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(HandledPackage duinoPackage) {
        super.onPostExecute(duinoPackage);
        delegate.onComplete(duinoPackage);
    }
}
