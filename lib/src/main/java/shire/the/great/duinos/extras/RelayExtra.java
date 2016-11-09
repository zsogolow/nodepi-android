package shire.the.great.duinos.extras;

/**
 * Created by ZachS on 11/8/2016.
 */

public class RelayExtra extends DuinoExtra {

    private boolean lightsState;

    public RelayExtra(String extra) {
        super(extra);

        if( extra.equals("1")) {
            lightsState = true;
        } else {
            lightsState = false;
        }
    }

    @Override
    public String toString() {
        return lightsState ? "Lights On!" : "Lights Off!";
    }

    public boolean lightsOn() {
        return lightsState;
    }
}
