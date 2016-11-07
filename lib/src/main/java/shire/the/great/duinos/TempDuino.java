package shire.the.great.duinos;

import java.util.Date;

/**
 * Created by ZachS on 11/6/2016.
 */

public class TempDuino extends Duino {
    public TempDuino(int id, DuinoActions action, DuinoExtra extra, Date heartbeat) {
        super(id, DuinoTypes.Temperature, action, extra, heartbeat);
    }
}
