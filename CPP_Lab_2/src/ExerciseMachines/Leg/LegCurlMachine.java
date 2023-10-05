package ExerciseMachines.Leg;

import Enums.Exercises;
import Enums.MuscleGroups;

import java.util.ArrayList;
import java.util.List;

public class LegCurlMachine extends AbstractLegExerciseMachine {
    public LegCurlMachine() {
        super();
        this.name = "Leg Curl Machine";
        this.exerciseDuration = 7;
        this.exercises = new ArrayList<>(List.of(Exercises.LEG_CURL));
        this.musclesBenefited = new ArrayList<>(List.of(MuscleGroups.LEGS));
    }

    @Override
    public void perform(Exercises exercise) {
        System.out.printf("Performing %s on a %s...%n", exercise.name(), this.name);
    }
}
