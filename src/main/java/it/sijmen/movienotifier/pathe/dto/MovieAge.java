package it.sijmen.movienotifier.pathe.dto;

public class MovieAge {
    public static final int _12 = 2;
    public static final int _16 = 3;
    public static final int _6 = 1;
    public static final int _AL = 0;
    public static final int _UNDEFINED = -1;

    public static int fromAgeString(String age) {
        if (age == null) {
            return _UNDEFINED;
        }
        if (age.equalsIgnoreCase("6")) {
            return _6;
        }
        if (age.equalsIgnoreCase("12")) {
            return _12;
        }
        if (age.equalsIgnoreCase("16")) {
            return _16;
        }
        return _AL;
    }

}
