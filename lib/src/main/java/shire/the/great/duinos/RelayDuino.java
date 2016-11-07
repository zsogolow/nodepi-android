package shire.the.great.duinos;

import java.util.Date;

/**
 * Created by ZachS on 11/6/2016.
 */

public class RelayDuino extends Duino {
    public RelayDuino(int id, DuinoActions action, DuinoExtra extra, Date heartbeat) {
        super(id, DuinoTypes.Relay, action, extra, heartbeat);
    }
}
