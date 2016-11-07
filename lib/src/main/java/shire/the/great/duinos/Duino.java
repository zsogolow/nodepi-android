package shire.the.great.duinos;

import java.util.Date;

/**
 * Created by ZachS on 11/6/2016.
 */

public class Duino implements Comparable<Duino> {

    private int id;
    private DuinoTypes type;
    private DuinoActions action;
    private DuinoExtra extra;
    private Date heartbeat;

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