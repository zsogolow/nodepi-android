package shire.the.great.sockets;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import shire.the.great.duinos.Duino;
import shire.the.great.duinos.actions.DuinoActions;
import shire.the.great.duinos.extras.DuinoExtra;
import shire.the.great.duinos.types.DuinoTypes;
import shire.the.great.duinos.GeneralDuino;
import shire.the.great.duinos.MotionDuino;
import shire.the.great.duinos.RelayDuino;
import shire.the.great.duinos.TempDuino;
import shire.the.great.duinos.extras.GeneralExtra;
import shire.the.great.duinos.extras.MotionExtra;
import shire.the.great.duinos.extras.RelayExtra;
import shire.the.great.duinos.extras.TempExtra;

/**
 * Created by ZachS on 11/8/2016.
 */

public class DuinoParser {
    public static Duino parse(JSONObject jsonObject) {
        try {
            int id = jsonObject.getInt("id");
            DuinoTypes type = DuinoTypes.parse(jsonObject.getString("type"));
            DuinoActions action = DuinoActions.parse(jsonObject.getString("action"));
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            Date heartbeat = format.parse(jsonObject.getString("heartbeat"));
            String extraString = jsonObject.getString("extra");
            DuinoExtra extra = null;
            Duino duino = null;

            switch (type) {
                case Relay:
                    extra = new RelayExtra(extraString);
                    duino = new RelayDuino(id, action, extra, heartbeat);
                    break;
                case Temperature:
                    extra = new TempExtra(extraString);
                    duino = new TempDuino(id, action, extra, heartbeat);
                    break;
                case Motion:
                    extra = new MotionExtra(extraString);
                    duino = new MotionDuino(id, action, extra, heartbeat);
                    break;
                case General:
                case Unknown:
                    extra = new GeneralExtra(extraString);
                    duino = new GeneralDuino(id, action, extra, heartbeat);
                default:
                    break;
            }

            return duino;

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
