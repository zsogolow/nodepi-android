package shire.the.great.duinos;

import java.util.Date;

import shire.the.great.duinos.actions.DuinoActions;
import shire.the.great.duinos.extras.DuinoExtra;
import shire.the.great.duinos.types.DuinoTypes;

/**
 * Created by ZachS on 11/6/2016.
 */

public abstract class Duino implements Comparable<Duino> {

    protected int id;
    protected DuinoTypes type;
    protected DuinoActions action;
    protected DuinoExtra extra;
    protected Date heartbeat;

    public Duino(int id, DuinoTypes type, DuinoActions action, DuinoExtra extra, Date heartbeat) {
        this.id = id;
        this.type = type;
        this.action = action;
        this.extra = extra;
        this.heartbeat = heartbeat;
    }

    public int getId() {
        return id;
    }

    public DuinoTypes getType() {
        return type;
    }

    public DuinoActions getAction() {
        return action;
    }

    public DuinoExtra getExtra() {
        return extra;
    }

    public Date getHeartbeat() {
        return heartbeat;
    }

    @Override
    public int compareTo(Duino o) {
        return id - o.getId();
    }
}