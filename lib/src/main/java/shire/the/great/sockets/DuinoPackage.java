package shire.the.great.sockets;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import shire.the.great.duinos.Duino;
import shire.the.great.duinos.DuinoActions;
import shire.the.great.duinos.DuinoExtra;
import shire.the.great.duinos.DuinoTypes;

/**
 * Created by ZachS on 11/6/2016.
 */

public class DuinoPackage {
    private JSONObject rawPackage;
    private DuinoActions packageType;

    public DuinoPackage(JSONObject pack, DuinoActions type) {
        this.rawPackage = pack;
        this.packageType = type;
    }

    public Duino handle() {
        try {
            JSONObject data = rawPackage.getJSONObject("data");
            int id = data.getInt("id");
            DuinoTypes type = DuinoTypes.parse(data.getString("type"));
            DuinoActions action = DuinoActions.parse(data.getString("action"));
            DuinoExtra extra = new DuinoExtra(data.getString("extra"));
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            Date heartbeat = format.parse(data.getString("heartbeat"));
            Duino duino = new Duino(id, type, action, extra, heartbeat);
            return duino;
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getRawPackage() {
        return rawPackage;
    }

    public DuinoActions getPackageType() {
        return packageType;
    }
}

//    function parseDuinos(data) {
//        var dataArray = [];
//        for (var i = 0; i < data.length; i++) {
//            dataArray.push(data[i]);
//        }
//        var parsedDuinos = [];
//        var duinoLength = 4;
//        var responses = dataArray.length / duinoLength;
//
//        for (var i = 0; i < responses; i++) {
//            var duino = readDuino(dataArray, i * duinoLength, duinoLength);
//            parsedDuinos.push(duino);
//        }
//        return parsedDuinos;
//    }
//
//    function readDuino(data, index, length) {
//        var id = data[index + 0];
//        var action = data[index + 1];
//        var type = data[index + 2];
//        var extra = data[index + 3];
//
//        var realType = duinos.getDuinoType(type);
//        var realAction = duinos.getDuinoAction(action);
//        var duino = new Duino(id, realType, realAction, extra);
//        if (duino.id > 0 && duino.type != 'unknown') {
//            // considered alive!
//            duino.heartbeat = new Date();
//        }
//
//        return duino;
//    }