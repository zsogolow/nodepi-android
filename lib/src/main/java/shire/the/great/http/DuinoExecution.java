package shire.the.great.http;

import shire.the.great.duinos.DuinoActions;

/**
 * Created by ZachS on 11/6/2016.
 */

public class DuinoExecution {
    private int id;
    private DuinoActions action;

    public DuinoExecution(int id, DuinoActions action) {
        this.id = id;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public DuinoActions getAction() {
        return action;
    }

    public String getUri() {
        return action.actionUri;
    }
}
