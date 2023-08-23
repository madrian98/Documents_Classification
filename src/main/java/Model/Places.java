package Model;

import java.util.Arrays;



public enum Places {
    WEST_GERMANY(0),
    USA(1),
    FRANCE(2),
    UK(3),
    CANADA(4),
    JAPAN(5);

    public static final int NUMBER_OF_PLACES = 6;

    public final int id;

    Places(int id) {
        this.id = id;
    }

    public static Places findById(int id) {
        return Arrays.stream(Places.values()).filter(country1 -> country1.id == id).findAny().orElseThrow(IllegalStateException::new);
    }
}