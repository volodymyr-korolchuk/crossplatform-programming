package Enums;

import java.util.ArrayList;
import java.util.List;

public enum MuscleGroups {
    ARM,
    BACK,
    CORE,
    LEGS;

    public ArrayList<MuscleGroups> getArmMuscles() {
        return new ArrayList<>(List.of(ARM));
    }
    public ArrayList<MuscleGroups> getBackMuscles() {
        return new ArrayList<>(List.of(BACK));
    }
    public ArrayList<MuscleGroups> getCoreMuscles() {
        return new ArrayList<>(List.of(CORE));
    }
    public ArrayList<MuscleGroups> getLegMuscles() {
        return new ArrayList<>(List.of(LEGS));
    }

}