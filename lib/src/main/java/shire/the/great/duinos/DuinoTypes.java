package shire.the.great.duinos;

/**
 * Created by ZachS on 11/6/2016.
 */

public enum DuinoTypes {

    Unknown("unknown", 20),
    General("general", 21),
    Relay("relay", 22),
    Temperature("temp", 23),
    Motion("motion", 24);

    public String typeName;
    public int typeIdentifier;

    DuinoTypes(String name, int typeIdentifier) {
        this.typeName = name;
        this.typeIdentifier = typeIdentifier;
    }


    public static DuinoTypes parse(String name) {
        for (DuinoTypes type :
                DuinoTypes.values()) {
            if (type.typeName.equals(name)) {
                return type;
            }
        }

        return Unknown;
    }


    public static DuinoTypes parse(int id) {
        for (DuinoTypes type :
                DuinoTypes.values()) {
            if (type.typeIdentifier == id) {
                return type;
            }
        }

        return Unknown;
    }
}
