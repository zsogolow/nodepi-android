package shire.the.great.nodepi.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import shire.the.great.constants.Constants;
import shire.the.great.duinos.Duino;
import shire.the.great.duinos.DuinoActions;
import shire.the.great.duinos.DuinoExtra;
import shire.the.great.duinos.DuinoTypes;
import shire.the.great.nodepi.R;
import shire.the.great.nodepi.adapters.DuinosArrayAdapter;
import shire.the.great.nodepi.tasks.AsyncResult;
import shire.the.great.nodepi.tasks.GetJsonTask;
import shire.the.great.nodepi.tasks.PackageHandlerTask;
import shire.the.great.sockets.DuinoPackage;
import shire.the.great.sockets.HandledPackage;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private Emitter.Listener onDataListener = new Emitter.Listener() {
        @Override
        public void call(final Object[] args) {
            getActivity().runOnUiThread(new Runnable() {
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
                                        onSocketPackageReceived(output);
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
    };

    // the socket
    private Socket mSocket;
    private Map<Integer, Duino> mDuinos;
    private RecyclerView mRecyclerView;
    private DuinosArrayAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            mSocket = IO.socket(Constants.NodePiLocal);
        } catch (URISyntaxException e) {
        }

        mSocket.on(Constants.OnData, onDataListener);
        mSocket.connect();

        mDuinos = new HashMap<>();

        updateDuinosState();

        mRecyclerView = (RecyclerView) findViewById(R.id.duinosListView);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new DuinosArrayAdapter(getDuinosList());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // disconnect the socket
        mSocket.disconnect();
        mSocket.off(Constants.OnData, onDataListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id. action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_refresh:
                // User chose the "refresh" action
                clearDuinosState();
                updateDuinosState();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private Activity getActivity() {
        return this;
    }

    // TODO: 11/6/2016
    private void onUpdatedDuinos(List<Duino> duinos) {
        mDuinos.clear();
        for (Duino duino :
                duinos) {
            updateDuino(duino);
        }

        mAdapter.swap(getDuinosList());
    }

    public void updateDuino(Duino duino) {
        mDuinos.put(duino.getId(), duino);
    }

    // TODO: 11/6/2016
    private void onSocketPackageReceived(HandledPackage handledPackage) {
        Duino duino = handledPackage.getDuino();
        updateDuino(duino);
        mAdapter.swap(getDuinosList());
    }

    public void clearDuinosState() {
        mDuinos.clear();
        mAdapter.swap(getDuinosList());
    }

    public void updateDuinosState() {
        AsyncTask task = new GetJsonTask(new AsyncResult<JSONObject>() {
            @Override
            public void onComplete(JSONObject output) {
                List<Duino> duinos = parseDuinosState(output);
                onUpdatedDuinos(duinos);
            }
        }).execute(Constants.GetDuinosState);
    }

    private List<Duino> parseDuinosState(JSONObject jsonObject) {
        ArrayList<Duino> duinos = new ArrayList<>();
        for (int i = 1; i <= Constants.NumDuinos; i++) {
            try {
                JSONObject innerObject = jsonObject.getJSONObject(i + "");
                int id = innerObject.getInt("id");
                DuinoTypes type = DuinoTypes.parse(innerObject.getString("type"));
                DuinoActions action = DuinoActions.parse(innerObject.getString("action"));
                DuinoExtra extra = new DuinoExtra(innerObject.getString("extra"));
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
                Date heartbeat = format.parse(innerObject.getString("heartbeat"));
                Duino duino = new Duino(id, type, action, extra, heartbeat);
                duinos.add(duino);
            } catch (JSONException | NullPointerException | ParseException e) {
                e.printStackTrace();
            }
        }
        return duinos;
    }

    public Map<Integer, Duino> getDuinos() {
        return mDuinos;
    }

    public List<Duino> getDuinosList() {
        List<Duino> duinos = new ArrayList<>(mDuinos.values());
        Collections.sort(duinos);
        return duinos;
    }
}
