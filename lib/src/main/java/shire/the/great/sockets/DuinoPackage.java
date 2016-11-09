package shire.the.great.sockets;

import org.json.JSONException;
import org.json.JSONObject;

import shire.the.great.duinos.Duino;
import shire.the.great.duinos.actions.DuinoActions;


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
            return DuinoParser.parse(data);
        } catch (JSONException e) {
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
