package shire.the.great.nodepi.sockets;

import android.os.AsyncTask;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import shire.the.great.duinos.actions.DuinoActions;
import shire.the.great.nodepi.activities.MainActivity;
import shire.the.great.nodepi.tasks.AsyncResult;
import shire.the.great.nodepi.tasks.PackageHandlerTask;
import shire.the.great.sockets.DuinoPackage;
import shire.the.great.sockets.HandledPackage;

/**
 * Created by ZachS on 11/8/2016.
 */

public class SocketEmitterListener implements Emitter.Listener {

    private MainActivity _activity;

    public SocketEmitterListener(MainActivity activity) {
        this._activity = activity;
    }

    @Override
    public void call(final Object... args) {
        this._activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject[] datas = new JSONObject[args.length];
                for (int i = 0; i < args.length; i++) {
                    datas[i] = (JSONObject) args[i];
                }

                for (int i = 0; i < args.length; i++) {
                    JSONObject data = datas[i];
                    try {
                        DuinoActions realAction = DuinoActions.parse(data.getString("type"));
                        DuinoPackage duinoPackage = new DuinoPackage(data, realAction);
                        if (realAction != DuinoActions.Empty) {
                            AsyncTask task = new PackageHandlerTask(new AsyncResult<HandledPackage>() {
                                @Override
                                public void onComplete(HandledPackage output) {
                                    _activity.onSocketPackageReceived(output);
                                }
                            }).execute(duinoPackage);
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            }
        });
    }
}
