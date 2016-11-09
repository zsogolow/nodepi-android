package shire.the.great.sockets;

import org.json.JSONObject;

import shire.the.great.duinos.Duino;
import shire.the.great.duinos.actions.DuinoActions;

/**
 * Created by ZachS on 11/6/2016.
 */

public class HandledPackage extends DuinoPackage {
    private Duino duino;

    public HandledPackage(JSONObject pack, DuinoActions type, Duino duino) {
        super(pack, type);
        this.duino = duino;
    }

    public Duino getDuino() {
        return duino;
    }
}
