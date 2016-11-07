package shire.the.great.duinos;

import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;

import shire.the.great.constants.Constants;

/**
 * Created by ZachS on 11/6/2016.
 */

public enum DuinoActions {
    Empty("empty", -1, ""),
    Ping("ping", 1, Constants.GetPing),
    Heartbeat("heartbeat", 2, ""),
    Blink("blink", 3, ""),
    RelayState("relay_state", 4, Constants.GetLightsState),
    RelayOn("relay_on", 5, Constants.PostLightsOn),
    RelayOff("relay_off", 6, Constants.PostLightsOff),
    SensorData("sensor_data", 7, "");

    public String actionName;
    public int actionIdentifier;
    public String actionUri;

    DuinoActions(String name, int identifier, String uri) {
        this.actionName = name;
        this.actionIdentifier = identifier;
        this.actionUri = uri;
    }

    public static DuinoActions parse(String name) {
        for (DuinoActions action :
                DuinoActions.values()) {
            if (action.actionName.equals(name)) {
                return action;
            }
        }

        return Empty;
    }

    public static DuinoActions parse(int id) {
        for (DuinoActions action :
                DuinoActions.values()) {
            if (action.actionIdentifier == id) {
                return action;
            }
        }

        return Empty;
    }
}