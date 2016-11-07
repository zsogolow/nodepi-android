package shire.the.great.sockets;

import shire.the.great.duinos.Duino;

/**
 * Created by ZachS on 11/6/2016.
 */

public class PackageHandler {
    public PackageHandler() {
    }

    public Duino handle(DuinoPackage pack) {
        return pack.handle();
    }
}
