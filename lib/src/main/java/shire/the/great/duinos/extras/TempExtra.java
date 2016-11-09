package shire.the.great.duinos.extras;

/**
 * Created by ZachS on 11/8/2016.
 */

public class TempExtra extends DuinoExtra {

    private long temperature;

    public TempExtra(String extra) {
        super(extra);

        temperature = Long.parseLong(extra);
    }

    @Override
    public String toString() {
        return temperature + " degrees F";
    }
}
