package Enums;

import java.util.ArrayList;
import java.util.List;


public enum Exercises {
    BANDED_TRICEPS_EXTENSION,
    BICEP_CURL,
    DINNER_CURL,
    LEG_CURL,
    LEG_EXTENSION,
    LEG_RAISES,
    REVERSE_GRIP_PREACHER_CURL,
    SEATED_CABLE_ROW,
    SEATED_DIP,
    TORSO_ROTATION,
    WIDE_GRIP_LAT_PULLDOWNS;

    public ArrayList<Exercises> getArmExercises() {
        return new ArrayList<>(List.of(
            BANDED_TRICEPS_EXTENSION,
            BICEP_CURL,
            DINNER_CURL,
            REVERSE_GRIP_PREACHER_CURL,
            SEATED_DIP)
        );
    }
    public ArrayList<Exercises> getBackExercises() {
        return new ArrayList<>(List.of(SEATED_CABLE_ROW, WIDE_GRIP_LAT_PULLDOWNS));
    }
    public ArrayList<Exercises> getCoreExercises() {
        return new ArrayList<>(List.of(TORSO_ROTATION, LEG_RAISES));
    }
    public ArrayList<Exercises> getLEgExercises() {
        return new ArrayList<>(List.of(LEG_CURL, LEG_EXTENSION));
    }
}
