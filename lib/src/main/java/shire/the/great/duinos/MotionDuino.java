package shire.the.great.duinos;

import java.util.Date;

/**
 * Created by ZachS on 11/6/2016.
 */

public class MotionDuino extends Duino {
    public MotionDuino(int id, DuinoActions action, DuinoExtra extra, Date heartbeat) {
        super(id, DuinoTypes.Motion, action, extra, heartbeat);
    }
}
