package ExerciseMachines.Leg;

import Enums.Exercises;
import Enums.MuscleGroups;

import java.util.ArrayList;
import java.util.List;

public class LegExtensionMachine extends AbstractLegExerciseMachine {
    public LegExtensionMachine() {
        super();
        this.name = "Leg Extension Machine";
        this.exerciseDuration = 6;
        this.exercises = new ArrayList<>(List.of(Exercises.LEG_EXTENSION));
        this.musclesBenefited = new ArrayList<>(List.of(MuscleGroups.LEGS));
    }

    @Override
    public void perform(Exercises exercise) {
        System.out.printf("Performing %s on a %s...%n", exercise.name(), this.name);
    }
}
