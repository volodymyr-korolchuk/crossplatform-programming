package ExerciseMachines.Arms;

import Enums.Exercises;
import Enums.MuscleGroups;

import java.util.ArrayList;
import java.util.List;

public class SeatedDipMachine extends AbstractArmExerciseMachine {
    public SeatedDipMachine() {
        super();
        this.name = "Seated Dip Machine";
        this.exerciseDuration = 7;
        this.exercises = new ArrayList<>(List.of(Exercises.SEATED_DIP));
        this.musclesBenefited = new ArrayList<>(List.of(MuscleGroups.ARM));
    }
}
