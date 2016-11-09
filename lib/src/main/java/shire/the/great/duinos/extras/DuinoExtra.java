package shire.the.great.duinos.extras;

import java.util.Objects;

/**
 * Created by ZachS on 11/6/2016.
 */

public abstract class DuinoExtra {

    protected String extra;

    public DuinoExtra(String extra) {
        this.extra = extra;
    }

    public String getExtra() {
        return extra;
    }

    public abstract String toString();
}
