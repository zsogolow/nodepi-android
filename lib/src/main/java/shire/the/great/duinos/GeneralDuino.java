package shire.the.great.duinos;

import java.util.Date;

import shire.the.great.duinos.actions.DuinoActions;
import shire.the.great.duinos.extras.DuinoExtra;
import shire.the.great.duinos.types.DuinoTypes;

/**
 * Created by ZachS on 11/8/2016.
 */

public class GeneralDuino extends Duino {

    public GeneralDuino(int id, DuinoActions action, DuinoExtra extra, Date heartbeat) {
        super(id, DuinoTypes.General, action, extra, heartbeat);
    }
}
